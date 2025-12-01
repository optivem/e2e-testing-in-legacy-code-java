package com.optivem.eshop.systemtest.core.drivers.external.tax.api;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.client.TaxApiClient;
import com.optivem.eshop.systemtest.core.drivers.commons.Result;

public class TaxApiDriver implements AutoCloseable {

    private final TaxApiClient taxApiClient;

    public TaxApiDriver(String baseUrl) {
        this.taxApiClient = new TaxApiClient(baseUrl);
    }

    public Result<Void> goToTax() {
        return taxApiClient.health().checkHealth();
    }

    @Override
    public void close() {
        Closer.close(taxApiClient);
    }
}

