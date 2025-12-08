package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidSuccessResult;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.base.BaseErpCommand;

public class GoToErp extends BaseErpCommand<CommandResult<Void, VoidSuccessResult>> {
    public GoToErp(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidSuccessResult> execute() {
        var result = driver.goToErp();
        return new CommandResult<>(result, context, VoidSuccessResult::new);
    }
}

