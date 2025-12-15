package com.optivem.eshop.systemtest.core.shop.driver.api.client.controllers;

import com.optivem.http.JsonHttpClient;
import com.optivem.http.HttpUtils;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.GetOrderResponse;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.requests.PlaceOrderRequest;
import com.optivem.eshop.systemtest.core.shop.driver.dtos.responses.PlaceOrderResponse;
import com.optivem.lang.Error;
import com.optivem.lang.Result;

public class OrderController {

    private static final String ENDPOINT = "/api/orders";

    private final JsonHttpClient httpClient;

    public OrderController(JsonHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Result<PlaceOrderResponse, Error> placeOrder(PlaceOrderRequest request) {
        var httpResponse = httpClient.post(ENDPOINT, request);
        return HttpUtils.getCreatedResultOrFailure(httpResponse, PlaceOrderResponse.class)
                .mapFailure(HttpUtils::convertProblemDetailToError);
    }

    public Result<GetOrderResponse, Error> viewOrder(String orderNumber) {
        var httpResponse = httpClient.get(ENDPOINT + "/" + orderNumber);
        return HttpUtils.getOkResultOrFailure(httpResponse, GetOrderResponse.class)
                .mapFailure(HttpUtils::convertProblemDetailToError);
    }

    public Result<Void, Error> cancelOrder(String orderNumber) {
        var httpResponse = httpClient.post(ENDPOINT + "/" + orderNumber + "/cancel");
        return HttpUtils.getNoContentResultOrFailure(httpResponse)
                .mapFailure(HttpUtils::convertProblemDetailToError);
    }
}

