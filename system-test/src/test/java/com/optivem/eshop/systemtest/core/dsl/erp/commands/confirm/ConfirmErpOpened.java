package com.optivem.eshop.systemtest.core.dsl.erp.commands.confirm;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.execute.GoToErp;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.BaseErpCommand;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class ConfirmErpOpened extends BaseErpCommand {
    public ConfirmErpOpened(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        var result = context.results().getResult(GoToErp.COMMAND_NAME, Void.class);
        assertThatResult(result).isSuccess();
    }
}

