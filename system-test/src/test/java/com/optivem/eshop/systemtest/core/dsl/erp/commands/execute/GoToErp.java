package com.optivem.eshop.systemtest.core.dsl.erp.commands.execute;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.system.commands.BaseErpCommand;

public class GoToErp extends BaseErpCommand {
    public static final String COMMAND_NAME = "GoToErp";

    public GoToErp(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public void execute() {
        var result = driver.goToErp();
        context.results().registerResult(COMMAND_NAME, result);
    }
}

