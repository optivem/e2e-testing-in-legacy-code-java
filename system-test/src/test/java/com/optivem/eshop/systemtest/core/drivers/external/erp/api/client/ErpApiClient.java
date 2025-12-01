package com.optivem.eshop.systemtest.core.drivers.external.erp.api.client;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpClient;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.controllers.ProductController;

import java.net.http.HttpClient;

public class ErpApiClient implements AutoCloseable {

    private final HttpClient client;
    private final HealthController healthController;
    private final ProductController productController;

    public ErpApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        var testHttpClient = new TestHttpClient(client, baseUrl);
        this.healthController = new HealthController(testHttpClient);
        this.productController = new ProductController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public ProductController products() {
        return productController;
    }

    @Override
    public void close() {
        Closer.close(client);
    }
}
