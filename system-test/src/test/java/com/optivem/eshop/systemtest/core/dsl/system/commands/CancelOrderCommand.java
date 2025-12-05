package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.results.Result;

public class CancelOrderCommand {
    private final ShopDriver driver;
    private String orderNumber;

    public CancelOrderCommand(ShopDriver driver) {
        this.driver = driver;
    }

    public CancelOrderCommand orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public Result<Void> execute() {
        return driver.cancelOrder(orderNumber);
    }
}

