package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.commons.dtos.GetOrderResponse;
import com.optivem.results.Result;

public class ViewOrderCommand {
    private final ShopDriver driver;
    private String orderNumber;

    public ViewOrderCommand(ShopDriver driver) {
        this.driver = driver;
    }

    public ViewOrderCommand orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public Result<GetOrderResponse> execute() {
        return driver.viewOrder(orderNumber);
    }
}

