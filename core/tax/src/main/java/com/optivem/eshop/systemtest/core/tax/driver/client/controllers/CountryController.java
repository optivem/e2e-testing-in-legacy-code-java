package com.optivem.eshop.systemtest.core.tax.driver.client.controllers;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxErrorConverter;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.client.dtos.responses.CountryDetailsDto;
import com.optivem.lang.Result;

public class CountryController {

    private static final String ENDPOINT = "/api/countries";

    private final TaxHttpClient httpClient;

    public CountryController(TaxHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<CountryDetailsDto, TaxError> getCountry(String country) {
        return httpClient.get(ENDPOINT + "/" + country, CountryDetailsDto.class)
                .mapError(TaxErrorConverter::from);
    }
}

