package com.optivem.eshop.systemtest.core.tax.driver.real.client;

import com.optivem.eshop.systemtest.core.tax.driver.client.BaseTaxClient;
import com.optivem.eshop.systemtest.core.tax.driver.client.commons.TaxHttpClient;

public class TaxRealClient extends BaseTaxClient {

    public TaxRealClient(TaxHttpClient taxHttpClient) {
        super(taxHttpClient);
    }
}

