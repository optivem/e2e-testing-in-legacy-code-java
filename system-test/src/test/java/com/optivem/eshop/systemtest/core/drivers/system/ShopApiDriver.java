package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.GetOrderResponse;

import java.net.http.HttpResponse;

/**
 * Driver for Shop API operations.
 * Encapsulates high-level business operations using the Shop API client.
 */
public class ShopApiDriver implements AutoCloseable {

    private final ShopApiClient shopApiClient;

    public ShopApiDriver(ShopApiClient shopApiClient) {
        this.shopApiClient = shopApiClient;
    }

    /**
     * Places an order and returns the order number.
     *
     * @param sku      the product SKU
     * @param quantity the quantity to order
     * @param country  the country code
     * @return the order number of the placed order
     */
    public String placeOrder(String sku, int quantity, String country) {
        var httpResponse = shopApiClient.orders().placeOrder(sku, String.valueOf(quantity), country);
        var response = shopApiClient.orders().assertOrderPlacedSuccessfully(httpResponse);
        return response.getOrderNumber();
    }

    /**
     * Gets the details of an order.
     *
     * @param orderNumber the order number
     * @return the order details
     */
    public GetOrderResponse getOrderDetails(String orderNumber) {
        var httpResponse = shopApiClient.orders().viewOrder(orderNumber);
        return shopApiClient.orders().assertOrderViewedSuccessfully(httpResponse);
    }

    /**
     * Cancels an order.
     *
     * @param orderNumber the order number to cancel
     */
    public void cancelOrder(String orderNumber) {
        var httpResponse = shopApiClient.orders().cancelOrder(orderNumber);
        shopApiClient.orders().assertOrderCancelledSuccessfully(httpResponse);
    }

    /**
     * Attempts to place an order with invalid data.
     *
     * @param sku      the product SKU
     * @param quantity the quantity (may be invalid)
     * @param country  the country code (may be invalid)
     * @return the HTTP response for validation
     */
    public HttpResponse<String> attemptPlaceOrder(String sku, String quantity, String country) {
        return shopApiClient.orders().placeOrder(sku, quantity, country);
    }

    /**
     * Asserts that an order placement failed with validation error.
     *
     * @param httpResponse the HTTP response
     */
    public void assertOrderPlacementFailed(HttpResponse<String> httpResponse) {
        shopApiClient.orders().assertOrderPlacementFailed(httpResponse);
    }

    /**
     * Gets the error message from a failed order placement.
     *
     * @param httpResponse the HTTP response
     * @return the error message
     */
    public String getOrderPlacementErrorMessage(HttpResponse<String> httpResponse) {
        return shopApiClient.orders().getErrorMessage(httpResponse);
    }

    @Override
    public void close() {
        if (shopApiClient != null) {
            shopApiClient.close();
        }
    }
}

