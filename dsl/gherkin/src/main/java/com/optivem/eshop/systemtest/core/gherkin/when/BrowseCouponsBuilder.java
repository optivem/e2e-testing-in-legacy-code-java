package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class BrowseCouponsBuilder {
    private final SystemDsl app;
    private final ScenarioDsl scenario;

    public BrowseCouponsBuilder(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
    }

    public ThenClause then() {
        var result = app.shop().browseCoupons().execute();
        return new ThenClause(app, scenario, null, result);
    }
}
