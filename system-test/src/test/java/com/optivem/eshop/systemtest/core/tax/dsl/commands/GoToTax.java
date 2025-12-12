package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;

public class GoToTax extends BaseTaxCommand<Void, VoidVerification> {
    public GoToTax(TaxDriver driver, Context context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToTax();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

