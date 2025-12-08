package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public class GoToShop extends BaseShopCommand<CommandResult<Void, VoidSuccessResult>> {
    public static final String COMMAND_NAME = "GoToShop";

    public GoToShop(ShopDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidSuccessResult> execute() {
        var result = driver.goToShop();
        context.results().registerResult(COMMAND_NAME, result);
        return new CommandResult<>(result, context, VoidSuccessResult::new);
    }
}

