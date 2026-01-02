package com.optivem.eshop.systemtest.core.shop.client.ui;

import com.microsoft.playwright.*;
import com.optivem.eshop.systemtest.core.shop.client.ui.pages.HomePage;
import com.optivem.lang.Closer;
import com.optivem.playwright.BrowserLifecycleExtension;
import com.optivem.playwright.PageClient;
import org.springframework.http.HttpStatus;


public class ShopUiClient implements AutoCloseable {

    private static final String CONTENT_TYPE = "content-type";
    private static final String TEXT_HTML = "text/html";
    private static final String HTML_OPENING_TAG = "<html";
    private static final String HTML_CLOSING_TAG = "</html>";

    private final String baseUrl;
    private final BrowserContext context;
    private final Page page;
    private final HomePage homePage;

    private Response response;

    public ShopUiClient(String baseUrl) {
        long totalStart = System.currentTimeMillis();
        this.baseUrl = baseUrl;

        // Get browser for current thread from BrowserLifecycleExtension
        Browser browser = BrowserLifecycleExtension.getBrowser();

        // Create isolated browser context for this test instance
        var contextOptions = new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                // Ensure complete isolation between parallel tests
                .setStorageStatePath(null);

        long contextStart = System.currentTimeMillis();
        this.context = browser.newContext(contextOptions);
        System.out.println("[PERF] Browser context creation took " + (System.currentTimeMillis() - contextStart) + "ms");

        // Each test gets its own page
        long pageStart = System.currentTimeMillis();
        this.page = context.newPage();
        System.out.println("[PERF] New page creation took " + (System.currentTimeMillis() - pageStart) + "ms");

        var pageClient = PageClient.builder(page)
                .baseUrl(baseUrl)
                .build();
        this.homePage = new HomePage(pageClient);
        
        System.out.println("[PERF] ShopUiClient constructor total took " + (System.currentTimeMillis() - totalStart) + "ms");
    }

    public HomePage openHomePage() {
        long start = System.currentTimeMillis();
        response = page.navigate(baseUrl);
        System.out.println("[PERF] page.navigate() took " + (System.currentTimeMillis() - start) + "ms");
        return homePage;
    }

    public boolean isStatusOk() {
        return response.status() == HttpStatus.OK.value();
    }

    public boolean isPageLoaded() {
        if (response == null || response.status() != HttpStatus.OK.value()) {
            return false;
        }

        var contentType = response.headers().get(CONTENT_TYPE);
        if (contentType == null || !contentType.equals(TEXT_HTML)) {
            return false;
        }

        var pageContent = page.content();
        return pageContent != null && pageContent.contains(HTML_OPENING_TAG) && pageContent.contains(HTML_CLOSING_TAG);
    }

    @Override
    public void close() {
        Closer.close(page);
        Closer.close(context);
        // Don't close browser - it's shared and managed by BrowserLifecycleExtension
    }
}

