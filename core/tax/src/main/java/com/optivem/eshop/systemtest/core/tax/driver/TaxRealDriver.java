package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.driver.dtos.error.TaxErrorResponse;
import com.optivem.eshop.systemtest.core.tax.client.TaxRealClient;
import com.optivem.eshop.systemtest.core.tax.client.commons.TaxHttpClient;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.ReturnsTaxRateRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.GetTaxResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;

import java.net.http.HttpClient;

public class TaxRealDriver implements TaxDriver {

    private final HttpClient httpClient;
    private final TaxRealClient taxClient;

    public TaxRealDriver(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        var taxHttpClient = new TaxHttpClient(httpClient, baseUrl);
        this.taxClient = new TaxRealClient(taxHttpClient);
    }

    @Override
    public void close() {
        Closer.close(httpClient);
    }

    @Override
    public Result<Void, TaxErrorResponse> goToTax() {
        return taxClient.checkHealth();
    }

    @Override
    public Result<Void, TaxErrorResponse> returnsTaxRate(ReturnsTaxRateRequest request) {
        // No-op for real driver - data already exists in real service
        return Result.success();
    }

    @Override
    public Result<GetTaxResponse, TaxErrorResponse> getTax(GetTaxRequest request) {
        return taxClient.getCountry(request.getCountry())
                .map(taxDetails -> GetTaxResponse.builder()
                        .country(taxDetails.getId())
                        .taxRate(taxDetails.getTaxRate())
                        .build());
    }
}
