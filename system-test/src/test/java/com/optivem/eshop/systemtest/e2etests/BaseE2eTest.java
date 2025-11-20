package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverCloser;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseE2eTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;
    private TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        shopDriver = createDriver();
        erpApiDriver = DriverFactory.createErpApiDriver();
        taxApiDriver = DriverFactory.createTaxApiDriver();
    }

    protected abstract ShopDriver createDriver();

    @AfterEach
    void tearDown() {
        DriverCloser.close(shopDriver);
        DriverCloser.close(erpApiDriver);
        DriverCloser.close(taxApiDriver);
    }

    @Test
    void shouldCalculateOriginalOrderPrice() {
        erpApiDriver.createProduct("HP-15", "109.95");
        shopDriver.placeOrder("ORD-1001", "HP-15", "5", "US");
        shopDriver.confirmOrderDetails("ORD-1001", "HP-15", "5", "PLACED");
    }
}

