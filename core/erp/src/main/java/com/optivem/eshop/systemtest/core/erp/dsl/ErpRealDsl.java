package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.erp.driver.ErpRealDriver;
import com.optivem.testing.dsl.UseCaseContext;

/**
 * ERP DSL that uses the real ERP driver to make actual HTTP calls to the ERP API.
 */
public class ErpRealDsl extends BaseErpDsl {

    public ErpRealDsl(String baseUrl, UseCaseContext context) {
        super(new ErpRealDriver(baseUrl), context);
    }
}

