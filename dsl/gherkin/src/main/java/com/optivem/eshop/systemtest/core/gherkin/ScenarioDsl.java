package com.optivem.eshop.systemtest.core.gherkin;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.given.GivenClause;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;
import com.optivem.lang.Closer;

public class ScenarioDsl implements AutoCloseable {
    private final SystemDsl app;

    public ScenarioDsl(SystemDsl app) {
        this.app = app;
    }

    public GivenClause given() {
        return new GivenClause(app);
    }

    public WhenClause when() {
        return new WhenClause(app);
    }

    @Override
    public void close() throws Exception {
        Closer.close(app);
    }
}
