package com.optivem.eshop.systemtest.core.dsl.tax.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.dsl.tax.commands.execute.GoToTax;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmTaxOpened extends BaseTaxCommand {
    public ConfirmTaxOpened(TaxApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        var result = context.results().getResult(GoToTax.COMMAND_NAME, Void.class);
        assertThatResult(result).isSuccess();
    }
}

