package com.optivem.eshop.systemtest.core.clients.system.api;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.HealthController;
import com.optivem.eshop.systemtest.core.clients.system.api.controllers.OrderController;

import java.net.http.HttpClient;

public class ShopApiClient implements AutoCloseable {

    private final HttpClient httpClient;
    private final TestHttpClient testHttpClient;
    private final HealthController healthController;
    private final OrderController orderController;

    public ShopApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(httpClient, baseUrl);
        this.healthController = new HealthController(testHttpClient);
        this.orderController = new OrderController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public OrderController orders() {
        return orderController;
    }

    @Override
    public void close() {
        httpClient.close();
    }
}

