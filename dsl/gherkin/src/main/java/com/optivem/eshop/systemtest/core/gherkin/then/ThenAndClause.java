package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;

import static com.optivem.eshop.systemtest.core.gherkin.GherkinDefaults.DEFAULT_ORDER_NUMBER;

public class ThenAndClause {
    private final SystemDsl app;
    private final String orderNumber;

    public ThenAndClause(SystemDsl app, String orderNumber) {
        this.app = app;
        this.orderNumber = orderNumber;
    }

    public OrderVerificationBuilder order(String orderNumber) {
        return new OrderVerificationBuilder(app, orderNumber);
    }

    public OrderVerificationBuilder order() {
        return new OrderVerificationBuilder(app, this.orderNumber != null ? this.orderNumber : DEFAULT_ORDER_NUMBER);
    }
}
