package com.optivem.eshop.systemtest.core.tax.driver;

import com.optivem.eshop.systemtest.core.tax.commons.TaxError;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.requests.GetTaxRequest;
import com.optivem.eshop.systemtest.core.tax.driver.dtos.responses.GetTaxResponse;
import com.optivem.lang.Result;

public interface TaxDriver extends AutoCloseable {
    Result<Void, TaxError> goToTax();
    Result<GetTaxResponse, TaxError> getTax(GetTaxRequest request);
}

