package com.optivem.eshop.systemtest.core.drivers.external;

import com.optivem.eshop.systemtest.core.clients.external.erp.ErpApiClient;

import java.math.BigDecimal;

/**
 * Driver for ERP API operations.
 * Encapsulates high-level business operations using the ERP API client.
 */
public class ErpApiDriver implements AutoCloseable {

    private final ErpApiClient erpApiClient;

    public ErpApiDriver(ErpApiClient erpApiClient) {
        this.erpApiClient = erpApiClient;
    }

    /**
     * Creates a product in the ERP system with a unique SKU.
     *
     * @param baseSku   the base SKU (will have a unique suffix added)
     * @param unitPrice the unit price of the product
     * @return the unique SKU of the created product
     */
    public String createProduct(String baseSku, BigDecimal unitPrice) {
        return erpApiClient.products().createProduct(baseSku, unitPrice);
    }

    @Override
    public void close() {
        if (erpApiClient != null) {
            erpApiClient.close();
        }
    }
}

