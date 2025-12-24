package com.optivem.eshop.systemtest.core.erp.dsl;

import com.optivem.eshop.systemtest.core.erp.driver.ErpStubDriver;
import com.optivem.testing.dsl.UseCaseContext;

/**
 * ERP DSL that uses the stub driver to configure WireMock stubs for the ERP API.
 * This allows tests to run without a real ERP system by stubbing responses.
 */
public class ErpStubDsl extends BaseErpDsl {

    public ErpStubDsl(String baseUrl, UseCaseContext context) {
        super(new ErpStubDriver(baseUrl), context);
    }
}

