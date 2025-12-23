package com.optivem.eshop.systemtest.core.erp.client;

import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.client.dtos.error.ExtErpErrorResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public abstract class BaseErpClient implements AutoCloseable {

    private final HttpClient rawHttpClient;
    protected final JsonHttpClient<ExtErpErrorResponse> httpClient;

    protected BaseErpClient(String baseUrl) {
        this.rawHttpClient = HttpClient.newHttpClient();
        this.httpClient = new JsonHttpClient<>(rawHttpClient, baseUrl, ExtErpErrorResponse.class);
    }

    @Override
    public void close() {
        Closer.close(rawHttpClient);
    }

    public Result<Void, ExtErpErrorResponse> checkHealth() {
        return httpClient.get("/health");
    }

    public Result<ExtProductDetailsResponse, ExtErpErrorResponse> getProduct(String sku) {
        return httpClient.get("/api/products/" + sku, ExtProductDetailsResponse.class);
    }
}
