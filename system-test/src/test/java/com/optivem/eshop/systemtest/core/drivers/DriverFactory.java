package com.optivem.eshop.systemtest.core.drivers;

import com.optivem.eshop.systemtest.core.clients.ClientFactory;
import com.optivem.eshop.systemtest.core.drivers.external.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopUiDriver;

/**
 * Factory for creating test drivers.
 * Drivers encapsulate high-level business operations and use clients internally.
 */
public class DriverFactory {

    public static ShopUiDriver createShopUiDriver() {
        var client = ClientFactory.createShopUiClient();
        return new ShopUiDriver(client);
    }

    public static ShopApiDriver createShopApiDriver() {
        var client = ClientFactory.createShopApiClient();
        return new ShopApiDriver(client);
    }

    public static ErpApiDriver createErpApiDriver() {
        var client = ClientFactory.createErpApiClient();
        return new ErpApiDriver(client);
    }

    public static TaxApiDriver createTaxApiDriver() {
        var client = ClientFactory.createTaxApiClient();
        return new TaxApiDriver(client);
    }
}

