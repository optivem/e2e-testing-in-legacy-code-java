package com.optivem.eshop.systemtest.core.clients.external.tax;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.external.tax.controllers.HealthController;

import java.net.http.HttpClient;

public class TaxApiClient implements AutoCloseable {

    private final HttpClient client;
    private final TestHttpClient testHttpClient;
    private final HealthController healthController;

    public TaxApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(client, baseUrl);
        this.healthController = new HealthController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    @Override
    public void close() {
        client.close();
    }
}

