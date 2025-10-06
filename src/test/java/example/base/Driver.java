package example.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;

public class Driver {

    private final Browser browser;
    private final Page page;

    public Driver(Browser browser, Page page) {
        this.browser = browser;
        this.page = page;
    }

    public Browser getBrowser() {
        return browser;
    }

    public Page getPage() {
        return page;
    }
}
