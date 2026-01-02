package com.optivem.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.*;

/**
 * JVM-wide browser pooling that works across multiple classloaders.
 * Uses System properties to store browser references that survive classloader boundaries.
 */
public class BrowserLifecycleExtension implements BeforeAllCallback, AfterAllCallback {
    
    // JVM-wide storage keys (survive across classloaders)
    private static final String BROWSER_POOL_KEY_PREFIX = "optivem.playwright.browser.";
    private static final String PLAYWRIGHT_POOL_KEY_PREFIX = "optivem.playwright.instance.";
    
    private static final boolean IS_HEADLESS = getHeadlessMode();
    private static final int SLOW_MO_MS = 100;
    private static final boolean DEFAULT_HEADLESS = false;

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

        return DEFAULT_HEADLESS;
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

    @Override
    public void beforeAll(ExtensionContext context) {
        // Extension registered but lifecycle managed by static methods
    }

    @Override
    public void afterAll(ExtensionContext context) {
        // Don't close browsers - they're shared across all test classes
    }

    /**
     * Public API: Get browser for current thread from JVM-wide pool
     */
    public static Browser getBrowser() {
        Thread currentThread = Thread.currentThread();
        String threadKey = currentThread.getName();
        
        String browserKey = BROWSER_POOL_KEY_PREFIX + threadKey;
        String playwrightKey = PLAYWRIGHT_POOL_KEY_PREFIX + threadKey;
        
        // Try to get existing browser from System properties  
        Object browserObj = System.getProperties().get(browserKey);
        if (browserObj instanceof Browser) {
            return (Browser) browserObj;
        }
        
        // Cache miss - create new browser
        Playwright playwright;
        Object playwrightObj = System.getProperties().get(playwrightKey);
        if (playwrightObj instanceof Playwright) {
            playwright = (Playwright) playwrightObj;
        } else {
            playwright = Playwright.create();
            System.getProperties().put(playwrightKey, playwright);
        }

        // Launch browser
        var launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(IS_HEADLESS)
                .setSlowMo(SLOW_MO_MS);

        Browser browser = playwright.chromium().launch(launchOptions);
        System.getProperties().put(browserKey, browser);
        
        return browser;
    }
}
