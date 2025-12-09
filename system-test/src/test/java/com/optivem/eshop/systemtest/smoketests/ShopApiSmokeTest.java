package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;

public class ShopApiSmokeTest extends BaseShopSmokeTest {

    @Override
    protected ShopDriver createShopDriver() {
        return DriverFactory.createShopApiDriver();
    }
}
