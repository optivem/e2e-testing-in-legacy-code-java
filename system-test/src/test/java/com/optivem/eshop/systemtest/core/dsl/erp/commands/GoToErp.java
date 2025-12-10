package com.optivem.eshop.systemtest.core.dsl.erp.commands;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.testing.dsl.CommandResult;
import com.optivem.testing.dsl.VoidVerification;
import com.optivem.testing.dsl.Context;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.base.BaseErpCommand;

public class GoToErp extends BaseErpCommand<Void, VoidVerification> {
    public GoToErp(ErpApiDriver driver, Context context) {
        super(driver, context);
    }

    @Override
    public CommandResult<Void, VoidVerification> execute() {
        var result = driver.goToErp();
        return new CommandResult<>(result, context, VoidVerification::new);
    }
}

