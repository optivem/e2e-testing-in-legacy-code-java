package com.optivem.eshop.systemtest.core.drivers.external.tax.api.client;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.TestHttpClient;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.client.controllers.HealthController;

public class TaxApiClient {

    private final HealthController healthController;

    public TaxApiClient(TestHttpClient testHttpClient) {
        this.healthController = new HealthController(testHttpClient);
    }

    public HealthController health() {
        return healthController;
    }
}

