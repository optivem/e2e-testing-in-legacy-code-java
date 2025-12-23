package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.client.dtos.CountryDetailsDto;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.lang.Result;

public abstract class BaseTaxClient {

    protected final TaxHttpClient httpClient;

    protected BaseTaxClient(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, TaxErrorResponse> checkHealth() {
        return httpClient.get("/health")
                .mapError(TaxErrorResponse::from);
    }

    public Result<CountryDetailsDto, TaxErrorResponse> getCountry(String country) {
        return httpClient.get("/api/countries/" + country, CountryDetailsDto.class)
                .mapError(TaxErrorResponse::from);
    }
}

