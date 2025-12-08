package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseErpCommand<T> extends BaseCommand<ErpApiDriver, T> {
    protected BaseErpCommand(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }
}

