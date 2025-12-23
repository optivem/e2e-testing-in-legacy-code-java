package com.optivem.eshop.systemtest.core.tax.client;

import com.optivem.eshop.systemtest.core.tax.client.commons.TaxErrorConverter;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.client.dtos.CountryDetailsDto;
import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.lang.Result;

public abstract class BaseTaxClient {

    protected final TaxHttpClient httpClient;

    protected BaseTaxClient(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<Void, TaxError> checkHealth() {
        return httpClient.get("/health")
                .mapError(TaxErrorConverter::from);
    }

    public Result<CountryDetailsDto, TaxError> getCountry(String country) {
        return httpClient.get("/api/countries/" + country, CountryDetailsDto.class)
                .mapError(TaxErrorConverter::from);
    }
}

