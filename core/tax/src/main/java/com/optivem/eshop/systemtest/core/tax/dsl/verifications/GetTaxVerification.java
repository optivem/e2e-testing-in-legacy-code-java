package com.optivem.eshop.systemtest.core.tax.dsl.verifications;

import com.optivem.eshop.systemtest.core.tax.driver.dtos.responses.GetTaxResponse;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTaxVerification extends ResponseVerification<GetTaxResponse, UseCaseContext> {

    public GetTaxVerification(GetTaxResponse response, UseCaseContext context) {
        super(response, context);
    }

    public GetTaxVerification country(String expectedCountry) {
        var actualCountry = response.getCountry();
        assertThat(actualCountry)
                .withFailMessage("Expected country to be '%s', but was '%s'", expectedCountry, actualCountry)
                .isEqualTo(expectedCountry);
        return this;
    }

    public GetTaxVerification countryFromParam(String countryParamAlias) {
        var expectedCountry = context.getParamValue(countryParamAlias);
        return country(expectedCountry);
    }

    public GetTaxVerification taxRate(BigDecimal expectedTaxRate) {
        var actualTaxRate = response.getTaxRate();
        assertThat(actualTaxRate)
                .withFailMessage("Expected tax rate to be %s, but was %s", expectedTaxRate, actualTaxRate)
                .isEqualTo(expectedTaxRate);
        return this;
    }

    public GetTaxVerification taxRate(double expectedTaxRate) {
        return taxRate(BigDecimal.valueOf(expectedTaxRate));
    }

    public GetTaxVerification taxRate(String expectedTaxRate) {
        return taxRate(new BigDecimal(expectedTaxRate));
    }

    public GetTaxVerification taxRateIsPositive() {
        var actualTaxRate = response.getTaxRate();
        assertThat(actualTaxRate)
                .withFailMessage("Expected tax rate to be positive, but was %s", actualTaxRate)
                .isPositive();
        return this;
    }
}

