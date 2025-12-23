package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.commons.driver.Driver;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.requests.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.responses.GetProductResponse;
import com.optivem.lang.Result;

public interface ErpDriver extends Driver {
    Result<Void, Error> goToErp();
    Result<Void, Error> returnsProduct(ReturnsProductRequest request);
    Result<GetProductResponse, Error> getProduct(GetProductRequest request);
}
