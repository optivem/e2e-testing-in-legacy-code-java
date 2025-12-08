package com.optivem.eshop.systemtest.core.dsl.erp.commands.base;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseCommand<ErpApiDriver, CommandResult<TResponse, TVerification>> {
    protected BaseErpCommand(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }
}

