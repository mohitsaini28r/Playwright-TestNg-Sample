package example.pages;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GoogleHomePage {
    private final Page page;

    // Resilient locators for “Images” (varies slightly by locale/markup) and logo
    private final Locator imagesLink;
    private final Locator googleLogo;

    public GoogleHomePage(Page page) {
        this.page = page;
        this.imagesLink = page.locator(
                "a[aria-label='Images'], a[aria-label*='Images'], a:has-text('Images')"
        ).first();
        this.googleLogo = page.locator("img[alt='Google']").first();
    }

    /** Navigate to Google and minimize consent noise; do not throw if no consent is present. */
    public void open() {
        // Navigate to Google with proper wait
        page.navigate("https://www.google.com/?hl=en&pccc=1");
        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        dismissConsentIfPresent();

        // Ensure the page chrome is ready
        page.locator("textarea[name='q'], input[name='q']").first().waitFor();
    }

    /** Visibility checks */
    public boolean isImagesLinkVisible() { return imagesLink.isVisible(); }
    public boolean isLogoVisible()       { return googleLogo.isVisible(); }

    /** “Clickable” check without navigation: trial click throws if not actionable */
    public void assertImagesLinkClickable() {
        imagesLink.click(new Locator.ClickOptions().setTrial(true)); // no nav; validates clickability
    }

    // ---------- helpers ----------
    private void dismissConsentIfPresent() {
        // Main document variants (short, non-fatal attempts)
        clickIfPresent("button#L2AGLb");
        clickIfPresent("button:has-text('I agree')");
        clickIfPresent("button:has-text('Accept all')");
        clickIfPresent("[role='dialog'] button:has-text('Accept')");

        // If consent appears in an iframe, iterate frames safely
        for (Frame f : page.frames()) {
            String u = f.url();
            if (u.contains("consent.")) {
                clickIfPresent(f, "button#L2AGLb");
                clickIfPresent(f, "button:has-text('I agree')");
                clickIfPresent(f, "button:has-text('Accept all')");
                clickIfPresent(f, "[role='dialog'] button:has-text('Accept')");
            }
        }
    }

    private void clickIfPresent(String selector) {
        try {
            Locator l = page.locator(selector);
            if (l.count() > 0) l.first().click(new Locator.ClickOptions().setTimeout(1200));
        } catch (Throwable ignored) {}
    }

    private void clickIfPresent(Frame f, String selector) {
        try {
            Locator l = f.locator(selector);
            if (l.count() > 0) l.first().click(new Locator.ClickOptions().setTimeout(1200));
        } catch (Throwable ignored) {}
    }
}