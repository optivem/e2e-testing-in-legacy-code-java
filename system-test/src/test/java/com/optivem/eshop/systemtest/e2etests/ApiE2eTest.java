package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;

class ApiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopApiDriver();
    }
}
