package com.optivem.eshop.systemtest.core.drivers.external.erp.api.client;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpClient;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.controllers.HealthController;
import com.optivem.eshop.systemtest.core.drivers.external.erp.api.client.controllers.ProductController;

public class ErpApiClient {

    private final HealthController healthController;
    private final ProductController productController;

    public ErpApiClient(TestHttpClient testHttpClient) {
        this.healthController = new HealthController(testHttpClient);
        this.productController = new ProductController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }

    public ProductController products() {
        return productController;
    }
}
