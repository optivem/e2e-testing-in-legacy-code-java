package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;

public class BrowseCouponsBuilder extends BaseWhenBuilder {
    public BrowseCouponsBuilder(SystemDsl app, ScenarioDsl scenario) {
        super(app, scenario);
    }

    @Override
    protected ExecutionResult execute(SystemDsl app) {
        var result = app.shop()
                .browseCoupons()
                .execute();

        return ExecutionResult.builder(result)
                .build();
    }
}
