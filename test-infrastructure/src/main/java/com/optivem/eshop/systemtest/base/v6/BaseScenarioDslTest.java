package com.optivem.eshop.systemtest.base.v6;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.ChannelExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseScenarioDslTest extends BaseConfigurableTest {
    private Playwright playwright;
    private Browser browser;
    protected ScenarioDsl scenario;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    void closeBrowser() {
        Closer.close(browser);
        Closer.close(playwright);
    }

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        var app = new SystemDsl(configuration, browser);
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(scenario);
    }
}
