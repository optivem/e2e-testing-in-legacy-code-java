package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ui.ShopUiDriver;

public class ShopUiSmokeTest extends BaseShopSmokeTest {

    @Override
    protected ShopDriver createShopDriver() {
        return DriverFactory.createShopUiDriver();
    }
}
