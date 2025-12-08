package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.CommandResult;
import com.optivem.eshop.systemtest.core.dsl.commons.commands.VoidVerification;
import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.base.BaseErpCommand;

public class GoToErp extends BaseErpCommand<Void, VoidVerification> {
    public GoToErp(ErpApiDriver driver, DslContext context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToErp();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

