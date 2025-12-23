package com.optivem.eshop.systemtest.core.erp.driver;

import com.optivem.eshop.systemtest.core.erp.client.ErpStubClient;
import com.optivem.eshop.systemtest.core.erp.client.dtos.ExtProductDetailsResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.ReturnsProductRequest;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.GetProductResponse;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.lang.Closer;
import com.optivem.lang.Result;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.math.BigDecimal;
import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * ErpStubDriver uses WireMock to stub ERP API responses.
 * This allows tests to run without a real ERP system.
 */
public class ErpStubDriver extends BaseErpDriver<ErpStubClient> {

    public ErpStubDriver(String baseUrl) {
        super(new ErpStubClient(baseUrl));
    }

    @Override
    public Result<Void, ErpErrorResponse> returnsProduct(ReturnsProductRequest request) {
        var extProductDetailsResponse = ExtProductDetailsResponse.builder()
                .id(request.getSku())
                .price(new BigDecimal(request.getPrice()))
                .build();

        client.configureGetProduct(extProductDetailsResponse);
        return Result.success();
    }
}
