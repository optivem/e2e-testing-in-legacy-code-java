package com.optivem.eshop.systemtest.core.tax.driver.client;

import com.optivem.http.HttpGateway;
import com.optivem.eshop.systemtest.core.tax.driver.client.controllers.HealthController;

public class TaxApiClient {

    private final HealthController healthController;

    public TaxApiClient(HttpGateway httpGateway) {
        this.healthController = new HealthController(httpGateway);
    }

    public HealthController health() {
        return healthController;
    }
}

