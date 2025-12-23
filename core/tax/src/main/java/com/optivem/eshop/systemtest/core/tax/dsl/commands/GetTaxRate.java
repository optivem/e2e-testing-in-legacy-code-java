package com.optivem.eshop.systemtest.core.tax.dsl.commands;

import com.optivem.eshop.systemtest.core.tax.driver.TaxDriver;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.requests.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.responses.GetTaxResponse;
import com.optivem.eshop.systemtest.core.tax.dsl.verifications.GetTaxVerification;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.BaseTaxCommand;
import com.optivem.eshop.systemtest.core.tax.dsl.commands.base.TaxUseCaseResult;

public class GetTaxRate extends BaseTaxCommand<GetTaxResponse, GetTaxVerification> {
    private static final String DEFAULT_COUNTRY = "DEFAULT_COUNTRY";

    private String countryValueOrAlias;

    public GetTaxRate(TaxDriver driver, UseCaseContext context) {
        super(driver, context);
        country(DEFAULT_COUNTRY);
    }

    public GetTaxRate country(String countryValueOrAlias) {
        this.countryValueOrAlias = countryValueOrAlias;
        return this;
    }

    @Override
    public TaxUseCaseResult<GetTaxResponse, GetTaxVerification> execute() {
        // Get aliased value from context for stub driver
        var countryAliasedValue = context.getParamValue(countryValueOrAlias);

        // TODO: VJ: I don't know if this is the right way, or we should decide here?
        var request = GetTaxRequest.builder()
                .countryRaw(countryValueOrAlias)  // Raw value for real driver
                .countryAliasedValue(countryAliasedValue)  // Aliased value for stub driver
                .build();

        var result = driver.getTax(request);

        return new TaxUseCaseResult<>(result, context, GetTaxVerification::new);
    }
}


