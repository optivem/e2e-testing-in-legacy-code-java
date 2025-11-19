package com.optivem.eshop.systemtest.core.drivers.external;

import com.optivem.eshop.systemtest.core.clients.external.tax.TaxApiClient;

/**
 * Driver for Tax API operations.
 * Encapsulates high-level business operations using the Tax API client.
 */
public class TaxApiDriver implements AutoCloseable {

    private final TaxApiClient taxApiClient;

    public TaxApiDriver(TaxApiClient taxApiClient) {
        this.taxApiClient = taxApiClient;
    }

    // Future: Add methods for tax-related operations when needed
    // Example: getTaxRateForCountry(String country)

    @Override
    public void close() {
        if (taxApiClient != null) {
            taxApiClient.close();
        }
    }
}

