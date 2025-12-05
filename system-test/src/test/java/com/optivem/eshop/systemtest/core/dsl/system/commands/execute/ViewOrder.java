package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseShopCommand;

public class ViewOrder extends BaseShopCommand {
    private String orderNumber;

    public ViewOrder(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    public ViewOrder orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public void execute() {
        var result = driver.viewOrder(orderNumber);
        context.results().registerResult("viewOrder", result);
    }
}

