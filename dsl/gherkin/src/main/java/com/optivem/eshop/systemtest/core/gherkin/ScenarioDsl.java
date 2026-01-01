package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenClause;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.lang.Closer;

public class ScenarioDsl implements AutoCloseable {
    private final SystemDsl setupDsl;  // For given() - fast API setup
    private final SystemDsl testDsl;   // For when()/then() - actual test
    private boolean executed = false;

    public ScenarioDsl(SystemDsl setupDsl, SystemDsl testDsl) {
        this.setupDsl = setupDsl;
        this.testDsl = testDsl;
    }

    public GivenClause given() {
        ensureNotExecuted();
        return new GivenClause(setupDsl, this);
    }

    public WhenClause when() {
        ensureNotExecuted();
        return new WhenClause(testDsl, this);
    }

    public void markAsExecuted() {
        this.executed = true;
    }

    private void ensureNotExecuted() {
        if (executed) {
            throw new IllegalStateException("Scenario has already been executed. " +
                    "Each test method should contain only ONE scenario execution (Given-When-Then). " +
                    "Split multiple scenarios into separate test methods.");
        }
    }

    @Override
    public void close() throws Exception {
        // DSLs are managed by BaseScenarioDslTest, don't close here
    }
}
