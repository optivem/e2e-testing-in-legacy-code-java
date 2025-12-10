package com.optivem.eshop.systemtest.core.dsl.tax.commands;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.base.BaseTaxCommand;

public class GoToTax extends BaseTaxCommand<Void, VoidVerification> {
    public GoToTax(TaxApiDriver driver, Context context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToTax();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

