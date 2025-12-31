package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.ExecutionResult;
import com.optivem.eshop.systemtest.core.gherkin.ScenarioDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.commands.base.ShopUseCaseResult;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

public class ThenClause {
    private final SystemDsl app;
    private final ScenarioDsl scenario;
    private final ExecutionResult executionResult;


    public ThenClause(SystemDsl app, ScenarioDsl scenario, ExecutionResult executionResult) {
        this.app = app;
        this.scenario = scenario;
        this.executionResult = executionResult;
    }

    public ThenSuccessBuilder<?> shouldSucceed() {
        if (executionResult == null) {
            throw new IllegalStateException("Cannot verify success: no operation was executed");
        }
        scenario.markAsExecuted();
        var successVerification = executionResult.getResult().shouldSucceed();
        return new ThenSuccessBuilder<>(this, successVerification);
    }

    public ThenFailureBuilder shouldFail() {
        scenario.markAsExecuted();
        return new ThenFailureBuilder(this, executionResult.getResult());
    }

    public ThenOrderBuilder order(String orderNumber) {
        scenario.markAsExecuted();
        return new ThenOrderBuilder(this, app, orderNumber);
    }

    public ThenOrderBuilder order() {
        var orderNumber = executionResult.getOrderNumber();
        return order(orderNumber != null ? orderNumber : DEFAULT_ORDER_NUMBER);
    }

    public ThenCouponBuilder coupon() {
        scenario.markAsExecuted();
        return new ThenCouponBuilder(app);
    }
}
