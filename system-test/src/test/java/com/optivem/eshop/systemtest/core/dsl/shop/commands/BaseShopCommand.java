package com.optivem.eshop.systemtest.core.dsl.shop.commands;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseShopCommand<T> extends BaseCommand<ShopDriver, T> {
    protected BaseShopCommand(ShopDriver driver, DslContext context) {
        super(driver, context);
    }
}
