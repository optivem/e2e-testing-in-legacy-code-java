package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseShopCommand;

public class CancelOrder extends BaseShopCommand {
    private String orderNumber;

    public CancelOrder(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public CancelOrder orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public void execute() {
        var result = driver.cancelOrder(orderNumber);
        context.results().registerResult("cancelOrder", result);
    }
}

