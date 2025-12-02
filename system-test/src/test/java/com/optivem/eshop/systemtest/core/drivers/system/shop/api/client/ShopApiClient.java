package com.optivem.eshop.systemtest.core.drivers.system.shop.api.client;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpClient;
import com.optivem.eshop.systemtest.core.drivers.system.shop.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.core.drivers.system.shop.api.client.controllers.OrderController;

public class ShopApiClient {

    private final HealthController healthController;
    private final OrderController orderController;

    public ShopApiClient(TestHttpClient testHttpClient) {
        this.healthController = new HealthController(testHttpClient);
        this.orderController = new OrderController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public OrderController orders() {
        return orderController;
    }
}

