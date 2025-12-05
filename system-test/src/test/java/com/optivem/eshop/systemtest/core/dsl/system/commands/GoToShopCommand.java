package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;

public class GoToShopCommand {
    private final ShopDriver driver;
    private final DslContext context;

    public GoToShopCommand(ShopDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public void execute() {
        var result = driver.goToShop();
        context.results().register("goToShop", result);
    }
}

