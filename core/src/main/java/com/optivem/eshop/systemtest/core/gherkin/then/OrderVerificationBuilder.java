package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.enums.OrderStatus;

public class OrderVerificationBuilder {
    private final SystemDsl app;
    private final String orderNumber;

    public OrderVerificationBuilder(SystemDsl app, String orderNumber) {
        this.app = app;
        this.orderNumber = orderNumber;
    }

    public OrderVerificationBuilder hasSku(String expectedSku) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .sku(expectedSku);
        return this;
    }

    public OrderVerificationBuilder hasTotalPrice(double expectedTotalPrice) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .totalPriceGreaterThanZero();
        return this;
    }

    public OrderVerificationBuilder hasStatus(OrderStatus expectedStatus) {
        app.shop().viewOrder()
                .orderNumber(orderNumber)
                .execute()
                .shouldSucceed()
                .status(expectedStatus);
        return this;
    }
}
