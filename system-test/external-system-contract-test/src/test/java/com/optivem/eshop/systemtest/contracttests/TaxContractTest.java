package com.optivem.eshop.systemtest.contracttests;

import com.optivem.eshop.systemtest.BaseSystemTest;
import org.junit.jupiter.api.Test;

public class TaxContractTest extends BaseSystemTest {
    @Test
    void shouldBeAbleToGetTaxRate() {
        app.tax().getTax()
                .country("US")
                .execute()
                .shouldSucceed()
                .country("US")
                .taxRateIsPositive();
    }
}
