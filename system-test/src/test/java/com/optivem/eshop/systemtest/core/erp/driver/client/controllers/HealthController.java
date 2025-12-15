package com.optivem.eshop.systemtest.core.erp.driver.client.controllers;

import com.optivem.http.JsonHttpClient;
import com.optivem.http.ProblemDetailResponse;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class HealthController {

    private static final String ENDPOINT = "/health";

    private final JsonHttpClient<ProblemDetailResponse> httpClient;

    public HealthController(JsonHttpClient<ProblemDetailResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, Error> checkHealth() {
        return httpClient.get(ENDPOINT, Void.class)
                .mapFailure(JsonHttpClient::convertProblemDetailToError);
    }
}

