package com.optivem.eshop.systemtest.core.dsl.system.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.results.Result;

public class GoToShopCommand {
    private final ShopDriver driver;

    public GoToShopCommand(ShopDriver driver) {
        this.driver = driver;
    }

    public Result<Void> execute() {
        return driver.goToShop();
    }
}

