package com.optivem.eshop.systemtest.core.dsl.erp.commands.base;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.testing.dsl.BaseCommand;
import com.optivem.testing.dsl.Context;

public abstract class BaseErpCommand<TResponse, TVerification> extends BaseCommand<ErpApiDriver, TResponse, TVerification> {
    protected BaseErpCommand(ErpApiDriver driver, Context context) {
        super(driver, context);
    }
}

