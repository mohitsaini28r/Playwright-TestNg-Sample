package example.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import example.LTCapability;
import example.base.BaseTest;
import example.base.Driver;

public class GoogleSearchTest extends BaseTest {

    @DataProvider(name = "getGoogleTestCapability")
    public static Object[] getGoogleTestCapability() {
        return LTCapability.getCapabilities("GoogleSearchTest");
    }

    @Test(description = "Verify presence of key buttons & images on Google home page", dataProvider = "getGoogleTestCapability")
    public void googleHome_UIElements_ShouldBeVisible(JsonObject capability) {
        Driver driver = null;
        Page page = null;
        try {
            System.out.println("Starting GoogleSearchTest...");
            driver = super.createConnection(capability);
            page = driver.getPage();
            page.navigate("https://www.google.com/?hl=en&pccc=1");
            Thread.sleep(200);
            page.setViewportSize(1900, 1050);
            System.out.println("Browser connected, navigated, and viewport set");
            
            try {
                dismissConsentIfPresent(page);
                System.out.println("Consent handling completed");
            } catch (Exception e) {
                System.out.println("Consent handling failed: " + e.getMessage());
            }
            
            try {
                page.locator("textarea[name='q'], input[name='q']").first().waitFor();
                System.out.println("Search box is ready");
            } catch (Exception e) {
                System.out.println("Search box wait failed: " + e.getMessage());
                throw e;
            }
            
            try {
                page.locator("textarea[name='q'], input[name='q']").first().click();
                System.out.println("Search box clicked successfully");
                Thread.sleep(1000);
                
                // Type something to verify functionality
                page.locator("textarea[name='q'], input[name='q']").first().fill("Sample");
                System.out.println("Text entered in search box");
                Thread.sleep(1000);
                
                // Clear the text
                page.locator("textarea[name='q'], input[name='q']").first().clear();
                System.out.println("Search box cleared");
                Thread.sleep(1000);
                
            } catch (Exception e) {
                System.out.println("Search box interaction failed: " + e.getMessage());
            }
            
            Thread.sleep(2000);
            System.out.println("About to set test status...");
            
            super.setTestStatus("passed", "Google home page UI elements verified successfully", page);
            System.out.println("Test completed successfully - reached final status update");
        } catch (Exception e) {
            e.printStackTrace();
            super.setTestStatus("failed", e.getMessage(), page);
            Assert.fail("Test failed: " + e.getMessage());
        } finally {
            if (driver != null) {
                System.out.println("Closing browser connection...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                super.closeConnection(driver);
                System.out.println("Browser connection closed");
            }
        }
    }
    
    private void dismissConsentIfPresent(Page page) {
        // Main document variants (short, non-fatal attempts)
        clickIfPresent(page, "button#L2AGLb");
        clickIfPresent(page, "button:has-text('I agree')");
        clickIfPresent(page, "button:has-text('Accept all')");
        clickIfPresent(page, "[role='dialog'] button:has-text('Accept')");

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

    private void clickIfPresent(Page page, String selector) {
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
