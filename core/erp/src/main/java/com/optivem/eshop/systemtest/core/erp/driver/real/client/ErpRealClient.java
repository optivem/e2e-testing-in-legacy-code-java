package com.optivem.eshop.systemtest.core.erp.driver.real.client;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.driver.client.BaseErpClient;
import com.optivem.eshop.systemtest.core.erp.driver.real.client.controllers.ProductController;
import com.optivem.http.JsonHttpClient;

/**
 * Real ERP client for making actual HTTP calls to the ERP API.
 */
public class ErpRealClient extends BaseErpClient {

    private final ProductController productController;

    public ErpRealClient(JsonHttpClient<ProblemDetailResponse> httpClient) {
        super(httpClient);
        this.productController = new ProductController(httpClient);
    }

    public ProductController products() {
        return productController;
    }
}

