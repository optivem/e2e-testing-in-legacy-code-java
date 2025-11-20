package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverCloser;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseE2eTest {

    protected ShopUiDriver shopUiDriver;
    protected ShopApiDriver shopApiDriver;
    protected ErpApiDriver erpApiDriver;
    protected TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        shopUiDriver = DriverFactory.createShopUiDriver();
        shopApiDriver = DriverFactory.createShopApiDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();
        taxApiDriver = DriverFactory.createTaxApiDriver();
    }

    @AfterEach
    void tearDown() {
        DriverCloser.close(shopUiDriver);
        DriverCloser.close(shopApiDriver);
        DriverCloser.close(erpApiDriver);
        DriverCloser.close(taxApiDriver);
    }
}

