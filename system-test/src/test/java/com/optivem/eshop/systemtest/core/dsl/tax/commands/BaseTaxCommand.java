package com.optivem.eshop.systemtest.core.dsl.tax.commands;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.BaseCommand;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseTaxCommand<T> extends BaseCommand<TaxApiDriver, T> {
    protected BaseTaxCommand(TaxApiDriver driver, DslContext context) {
        super(driver, context);
    }
}

