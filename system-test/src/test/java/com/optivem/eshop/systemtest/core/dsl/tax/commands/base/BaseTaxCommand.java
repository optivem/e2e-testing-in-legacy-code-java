package com.optivem.eshop.systemtest.core.dsl.tax.commands.base;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseTaxCommand<TResponse, TVerification> extends BaseCommand<TaxApiDriver, TResponse, TVerification> {
    protected BaseTaxCommand(TaxApiDriver driver, DslContext context) {
        super(driver, context);
    }
}

