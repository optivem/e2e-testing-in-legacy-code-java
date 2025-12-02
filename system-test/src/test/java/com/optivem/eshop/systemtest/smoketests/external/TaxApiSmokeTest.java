package com.optivem.eshop.systemtest.smoketests.external;

import com.optivem.eshop.systemtest.core.drivers.commons.clients.Closer;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.optivem.eshop.systemtest.core.drivers.commons.ResultAssert.assertThatResult;

public class TaxApiSmokeTest {

    private TaxApiDriver taxApiDriver;

    @BeforeEach
    void setUp() {
        this.taxApiDriver = DriverFactory.createTaxApiDriver();
    }

    @AfterEach
    void tearDown() {
        Closer.close(taxApiDriver);
    }

    @Test
    void shouldBeAbleToGoToTax() {
        var result = taxApiDriver.goToTax();
        assertThatResult(result).isSuccess();
    }
}

