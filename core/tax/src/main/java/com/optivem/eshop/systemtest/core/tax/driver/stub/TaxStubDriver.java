package com.optivem.eshop.systemtest.core.tax.driver.stub;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.stub.client.TaxStubClient;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.requests.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.responses.GetTaxResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.net.http.HttpClient;

public class TaxStubDriver implements TaxDriver {

    private final HttpClient httpClient;
    private final WireMock wireMock;
    private final TaxStubClient taxClient;

    public TaxStubDriver(String taxBaseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var taxHttpClient = new TaxHttpClient(httpClient, taxBaseUrl);
        this.taxClient = new TaxStubClient(taxHttpClient);

        var url = java.net.URI.create(taxBaseUrl);
        this.wireMock = new WireMock(url.getHost(), url.getPort());
    }

    @Override
    public Result<Void, TaxError> goToTax() {
        return taxClient.health().checkHealth();
    }

    @Override
    public Result<GetTaxResponse, TaxError> getTax(GetTaxRequest request) {
        return taxClient.countries().getCountry(request.getCountryAliasedValue())
                .map(taxDetails -> GetTaxResponse.builder()
                        .country(taxDetails.getId())
                        .taxRate(taxDetails.getTaxRate())
                        .build());
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }
}

