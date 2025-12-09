package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.systemtest.core.drivers.commons.ResultAssert.assertThatResult;

public abstract class BaseShopSmokeTest {

    private ShopDriver shopDriver;

    @BeforeEach
    void setUp() {
        shopDriver = createShopDriver();
    }

    protected abstract ShopDriver createShopDriver();

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
    }

    @Test
    void shouldBeAbleToGoToShop() {
        var result = shopDriver.goToShop();
        assertThatResult(result).isSuccess();
    }
}
