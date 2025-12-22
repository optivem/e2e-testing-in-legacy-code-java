package com.optivem.eshop.systemtest.core.erp.driver.client;

import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailConverter;
import com.optivem.eshop.systemtest.core.commons.error.ProblemDetailResponse;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Result;

/**
 * Base ERP client with common endpoints shared between real and stub implementations.
 */
public abstract class BaseErpClient {

    private static final String HEALTH_ENDPOINT = "/health";

    protected final JsonHttpClient<ProblemDetailResponse> httpClient;

    protected BaseErpClient(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Check the health of the ERP system.
     * @return Success if healthy, Error otherwise
     */
    public Result<Void, Error> checkHealth() {
        return httpClient.get(HEALTH_ENDPOINT)
                .mapFailure(ProblemDetailConverter::toError);
    }
}

