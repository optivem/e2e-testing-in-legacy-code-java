package com.optivem.eshop.systemtest.core.erp.driver.stub.client;

import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.erp.driver.client.BaseErpClient;
import com.optivem.http.JsonHttpClient;

/**
 * Stub ERP client for making HTTP calls to the WireMock stub.
 */
public class ErpStubClient extends BaseErpClient {

    public ErpStubClient(JsonHttpClient<ProblemDetailResponse> httpClient) {
        super(httpClient);
    }

    // Add stub-specific methods here if needed in the future
}

