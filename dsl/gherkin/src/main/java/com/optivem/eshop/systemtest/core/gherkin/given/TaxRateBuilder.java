package com.optivem.eshop.systemtest.core.gherkin.given;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.eshop.systemtest.core.gherkin.when.WhenClause;

public class TaxRateBuilder {
    private final GivenClause givenClause;
    private String country;
    private double taxRate;

    public TaxRateBuilder(GivenClause givenClause) {
        this.givenClause = givenClause;
    }

    public TaxRateBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public TaxRateBuilder withTaxRate(double taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public GivenClause and() {
        return givenClause;
    }

    public WhenClause when() {
        return givenClause.when();
    }

    void execute(SystemDsl app) {
        app.tax().returnsTaxRate()
                .country(this.country)
                .taxRate(this.taxRate)
                .execute()
                .shouldSucceed();
    }

    String getCountry() {
        return country;
    }
}

