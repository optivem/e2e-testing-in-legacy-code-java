package com.optivem.eshop.systemtest.contracttests;

import com.optivem.eshop.systemtest.BaseSystemTest;
import org.junit.jupiter.api.Test;

public class TaxContractTest extends BaseSystemTest {
    @Test
    void shouldBeAbleToGetTaxRate() {
        app.tax().returnsTaxRate()
                        .country("US")
                                .taxRate(12.30)
                .execute()
                .shouldSucceed();

        app.tax().getTaxRate()
                .country("US")
                .execute()
                .shouldSucceed()
                .country("US")
                .taxRateIsPositive();
    }
}
