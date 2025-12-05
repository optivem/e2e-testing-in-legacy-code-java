package com.optivem.eshop.systemtest.core.dsl.system.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseShopCommand;

public class GoToShop extends BaseShopCommand {

    public GoToShop(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        var result = driver.goToShop();
        context.results().registerResult("goToShop", result);
    }
}

