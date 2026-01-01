package com.optivem.eshop.systemtest.base.v7;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.playwright.BrowserLifecycleExtension;
import com.optivem.testing.channels.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ChannelExtension.class, BrowserLifecycleExtension.class})
public class BaseScenarioDslTest extends BaseConfigurableTest {
    protected SystemDsl setupDsl;  // API for fast setup
    protected SystemDsl testDsl;   // UI/API for actual test
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        
        // Setup DSL always uses API for fast test data creation
        setupDsl = SystemDsl.forChannel(configuration, "API");
        
        // Test DSL uses the channel specified by the test (UI or API)
        testDsl = new SystemDsl(configuration);
        
        scenario = new ScenarioDsl(setupDsl, testDsl);
    }

    @AfterEach
    void tearDown() {
        Closer.close(testDsl);
        Closer.close(setupDsl);
    }
}
