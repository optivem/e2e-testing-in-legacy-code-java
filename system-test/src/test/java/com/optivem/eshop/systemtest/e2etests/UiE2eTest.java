package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;

class UiE2eTest extends BaseE2eTest {

    @Override
    protected ShopDriver createDriver() {
        return DriverFactory.createShopUiDriver();
    }

    // TODO: Verifying the different responses, e.g. for the place order response
}

