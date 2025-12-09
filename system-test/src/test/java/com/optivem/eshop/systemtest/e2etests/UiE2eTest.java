package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.shop.ShopDriver;

class UiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createShopDriver() {
        return DriverFactory.createShopUiDriver();
    }

}

