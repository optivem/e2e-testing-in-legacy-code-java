package com.optivem.eshop.systemtest.core.dsl.erp;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.confirm.ConfirmErpOpened;
import com.optivem.eshop.systemtest.core.dsl.erp.commands.execute.GoToErp;

public class ErpDsl {
    private final ErpApiDriver driver;
    private final DslContext context;

    public ErpDsl(ErpApiDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public void goToErp() {
        new GoToErp(driver, context).execute();
    }

    public void confirmErpOpened() {
        new ConfirmErpOpened(driver, context).execute();
    }
}

