package com.optivem.eshop.systemtest.core.dsl.shop.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.BaseShopCommand;

public class ViewOrder extends BaseShopCommand {
    public static final String COMMAND_NAME = "ViewOrder";

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
        context.results().registerResult(COMMAND_NAME, result);
    }
}

