package com.optivem.eshop.systemtest.base.v7;

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

/**
 * Base test class following Playwright's recommended pattern:
 * - One Browser per test class (shared across all test methods)
 * - New BrowserContext per test (created in ShopUiClient)
 * - Uses @TestInstance(PER_CLASS) so Browser is shared
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({ChannelExtension.class})
public class BaseScenarioDslTest extends BaseConfigurableTest {
    // Shared between all tests in the class - per Playwright recommendation
    private Playwright playwright;
    private Browser browser;
    
    protected SystemDsl app;
    protected ScenarioDsl scenario;

    @BeforeAll
    void launchBrowser() {
        long start = System.currentTimeMillis();
        playwright = Playwright.create();
        
        boolean isHeadless = getHeadlessMode();
        var launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(isHeadless)
                .setSlowMo(100);

        browser = playwright.chromium().launch(launchOptions);
        System.out.println("[PERF] Browser creation took " + (System.currentTimeMillis() - start) + "ms");
    }

    @AfterAll
    void closeBrowser() {
        System.out.println("[CLEANUP] Closing browser for test class " + getClass().getSimpleName());
        if (browser != null) {
            try {
                browser.close();
            } catch (Exception e) {
                System.err.println("[WARN] Error closing browser: " + e.getMessage());
            }
        }
        if (playwright != null) {
            try {
                playwright.close();
            } catch (Exception e) {
                System.err.println("[WARN] Error closing playwright: " + e.getMessage());
            }
        }
    }

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        app = new SystemDsl(configuration, browser);
        scenario = new ScenarioDsl(app);
    }

    @AfterEach
    void tearDown() {
        Closer.close(app);
    }
    
    private static boolean getHeadlessMode() {
        String systemProperty = System.getProperty("headless");
        if (systemProperty != null) {
            return Boolean.parseBoolean(systemProperty);
        }

        String envVariable = System.getenv("HEADLESS");
        if (envVariable != null) {
            return Boolean.parseBoolean(envVariable);
        }

        if (isRunningInCI()) {
            return true;
        }

        return true; // default headless
    }

    private static boolean isRunningInCI() {
        return System.getenv("CI") != null ||
               System.getenv("JENKINS_HOME") != null ||
               System.getenv("GITHUB_ACTIONS") != null ||
               System.getenv("GITLAB_CI") != null ||
               System.getenv("CIRCLECI") != null ||
               System.getenv("TRAVIS") != null ||
               System.getenv("TEAMCITY_VERSION") != null ||
               System.getenv("BUILDKITE") != null;
    }
}
