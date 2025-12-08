package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerification;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.shop.commands.base.BaseShopCommand;

public class GoToShop extends BaseShopCommand<Void, VoidVerification> {
    public GoToShop(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToShop();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

