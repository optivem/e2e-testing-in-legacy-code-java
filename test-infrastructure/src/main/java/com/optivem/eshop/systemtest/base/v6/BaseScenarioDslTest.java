package com.optivem.eshop.systemtest.base.v6;

import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.lang.Closer;
import com.optivem.testing.channels.ChannelExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class BaseScenarioDslTest extends BaseConfigurableTest {
    protected SystemDsl setupDsl;
    protected SystemDsl testDsl;
    protected ScenarioDsl scenario;

    @BeforeEach
    void setUp() {
        var configuration = loadConfiguration();
        
        // Setup DSL always uses API for fast test data creation
        setupDsl = SystemDsl.forChannel(configuration, "API");
        
        // Test DSL uses the channel specified by the test
        testDsl = new SystemDsl(configuration);
        
        scenario = new ScenarioDsl(setupDsl, testDsl);
    }

    @AfterEach
    void tearDown() {
        Closer.close(scenario);
        Closer.close(testDsl);
        Closer.close(setupDsl);
    }
}
