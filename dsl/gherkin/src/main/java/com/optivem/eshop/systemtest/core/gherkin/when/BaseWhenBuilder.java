package com.optivem.eshop.systemtest.core.gherkin.when;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.gherkin.then.ThenClause;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

public abstract class BaseWhenBuilder {
    private final SystemDsl app;
    private final ScenarioDsl scenario;

    public BaseWhenBuilder(SystemDsl app, ScenarioDsl scenario) {
        this.app = app;
        this.scenario = scenario;
    }
    public ThenClause then() {
        var result = execute(app);
        return new ThenClause(app, scenario, result);
    }

    protected abstract ExecutionResult execute(SystemDsl app);
}
