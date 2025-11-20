package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.context.Context;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseE2eTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;
    private TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        var driverFactory = new DriverFactory();
        shopDriver = createDriver(driverFactory);
        erpApiDriver = driverFactory.createErpApiDriver();
        taxApiDriver = driverFactory.createTaxApiDriver();
    }

    protected abstract ShopDriver createDriver(DriverFactory driverFactory);

    @AfterEach
    void tearDown() {
        DriverCloser.close(shopDriver);
        DriverCloser.close(erpApiDriver);
        DriverCloser.close(taxApiDriver);
    }

    @Test
    void shouldCalculateOriginalOrderPrice() {
        erpApiDriver.createProduct("ABC-123", "109.95");
        shopDriver.placeOrder("ORD-1001", "ABC-123", "5", "US");
        shopDriver.confirmOrderDetails("ORD-1001", "ABC-123", "5", "PLACED");
    }

    @Test
    void shouldGenerateOrderNumber() {
        erpApiDriver.createProduct("ABC-123", "199.99");
        shopDriver.placeOrder("ORD-1001", "ABC-123", "5", "US");
        shopDriver.confirmOrderNumberGeneratedWithPrefix("ORD-1001", "ORD-");
    }
}

