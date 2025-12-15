package com.optivem.eshop.systemtest.core.shop.driver.api.client;

import com.optivem.http.JsonHttpClient;
import com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers.OrderController;

public class ShopApiClient {

    private final HealthController healthController;
    private final OrderController orderController;

    public ShopApiClient(JsonHttpClient httpGateway) {
        this.healthController = new HealthController(httpGateway);
        this.orderController = new OrderController(httpGateway);
    }

    public HealthController health() {
        return healthController;
    }

    public OrderController orders() {
        return orderController;
    }
}

