package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.context.Context;
import com.optivem.eshop.systemtest.core.clients.system.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.clients.system.api.dtos.OrderStatus;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ShopApiDriver implements ShopDriver {
    private final ShopApiClient apiClient;
    private final Context context;

    private final HashMap<String, HttpResponse<String>> ordersPlaced;
    private final HashMap<String, HttpResponse<String>> ordersViewed;
    private final HashMap<String, HttpResponse<String>> ordersCancelled;

    public ShopApiDriver(String baseUrl, Context context) {
        this.apiClient = new ShopApiClient(baseUrl);
        this.context = context;
        this.ordersPlaced = new HashMap<>();
        this.ordersViewed = new HashMap<>();
        this.ordersCancelled = new HashMap<>();
    }

    @Override
    public void goToShop() {
        var httpResponse = apiClient.echo().echo();
        apiClient.echo().assertEchoSuccessful(httpResponse);
    }

    @Override
    public void placeOrder(String orderNumberAlias, String skuAlias, String quantity, String country) {
        var skuValue = context.params().alias(skuAlias);

        var httpResponse = apiClient.orders().placeOrder(skuValue, quantity, country);
        registerOrderResponse(ordersPlaced, orderNumberAlias, httpResponse);

        var orderNumberValue = apiClient.orders().getOrderNumberIfOrderPlacedSuccessfully(httpResponse);
        orderNumberValue.ifPresent(v -> context.results().alias(orderNumberAlias, v));
    }

    @Override
    public void confirmOrderPlaced(String orderNumberAlias, String prefix) {
        var httpResponse = ordersPlaced.get(orderNumberAlias);
        var response = apiClient.orders().assertOrderPlacedSuccessfully(httpResponse);

        assertNotNull(response.getOrderNumber(), "Order number should be not be null");
        assertFalse(response.getOrderNumber().isEmpty(), "Order number should be not be empty");
        assertTrue(response.getOrderNumber().startsWith(prefix), "Order number should start with prefix: " + prefix);
    }

    // TODO: VJ: Consider deleting
    @Override
    public void viewOrderDetails(String orderNumberAlias) {
        var orderNumberValue = context.results().alias(orderNumberAlias);
        var httpResponse = apiClient.orders().viewOrder(orderNumberValue);
        registerOrderResponse(ordersViewed, orderNumberAlias, httpResponse);
    }

    @Override
    public void confirmOrderDetails(String orderNumberAlias, String skuAlias, String quantity, String status) {


        var httpResponse = ordersViewed.get(orderNumberAlias);

        if(httpResponse == null) {
            viewOrderDetails(orderNumberAlias);
            httpResponse = ordersViewed.get(orderNumberAlias);
        }

        var response = apiClient.orders().assertOrderViewedSuccessfully(httpResponse);

        var orderNumberValue = context.results().alias(orderNumberAlias);
        var skuValue = context.params().alias(skuAlias);

        // TODO: VJ: Confirm the order number value too

        assertEquals(skuValue, response.getSku());
        assertEquals(Integer.parseInt(quantity), response.getQuantity());

        var unitPrice = response.getUnitPrice();
        assertNotNull(unitPrice, "Unit price should not be null");
        assertTrue(unitPrice.compareTo(BigDecimal.ZERO) > 0, "Unit price should be positive");

        var totalPrice = response.getTotalPrice();
        assertNotNull(totalPrice, "Total price should not be null");
        assertTrue(totalPrice.compareTo(BigDecimal.ZERO) > 0, "Total price should be positive");
    }

    @Override
    public void confirmOrderStatusIsCancelled(String orderNumberAlias) {
        var httpResponse = ordersViewed.get(orderNumberAlias);
        var response = apiClient.orders().assertOrderViewedSuccessfully(httpResponse);
        assertEquals(OrderStatus.CANCELLED, response.getStatus(), "Order status should be CANCELLED");
    }

    @Override
    public void confirmOrderNumberGeneratedWithPrefix(String orderNumberAlias, String expectedPrefix) {
        // TODO: VJ: Check the generated order number prefix when placing the order

        var orderNumberValue = context.results().alias(orderNumberAlias);
        assertThat(orderNumberValue)
                .withFailMessage("Order number should start with prefix: " + expectedPrefix)
                .startsWith(expectedPrefix);

        // TODO: VJ: Can call order details?
    }

    @Override
    public void cancelOrder(String orderNumberAlias) {
        var orderNumberValue = context.results().alias(orderNumberAlias);
        var httpResponse = apiClient.orders().cancelOrder(orderNumberValue);
        registerOrderResponse(ordersCancelled, orderNumberAlias, httpResponse);
    }

    @Override
    public void confirmOrderCancelled(String orderNumberAlias) {
        var httpResponse = ordersCancelled.get(orderNumberAlias);
        apiClient.orders().assertOrderCancelledSuccessfully(httpResponse);
    }

    private static void registerOrderResponse(HashMap<String, HttpResponse<String>> map, String orderNumber, HttpResponse<String> httpResponse) {
        if(map.containsKey(orderNumber)) {
            throw new IllegalStateException("Response for order number " + orderNumber + " is already registered.");
        }

        map.put(orderNumber, httpResponse);
    }

    @Override
    public void close() {
        apiClient.close();
    }

//    private final ShopApiClient shopApiClient;
//
//    public ShopApiDriver(ShopApiClient shopApiClient) {
//        this.shopApiClient = shopApiClient;
//    }
//
//    public void assertEchoSuccessful() {
//        var httpResponse = shopApiClient.echo().echo();
//        shopApiClient.echo().assertEchoSuccessful(httpResponse);
//    }
//
//    public String placeOrder(String sku, int quantity, String country) {
//        var httpResponse = shopApiClient.orders().placeOrder(sku, String.valueOf(quantity), country);
//        var response = shopApiClient.orders().assertOrderPlacedSuccessfully(httpResponse);
//        return response.getOrderNumber();
//    }
//
//    public GetOrderResponse getOrderDetails(String orderNumber) {
//        var httpResponse = shopApiClient.orders().viewOrder(orderNumber);
//        return shopApiClient.orders().assertOrderViewedSuccessfully(httpResponse);
//    }
//
//    public void cancelOrder(String orderNumber) {
//        var httpResponse = shopApiClient.orders().cancelOrder(orderNumber);
//        shopApiClient.orders().assertOrderCancelledSuccessfully(httpResponse);
//    }
//
//    public HttpResponse<String> attemptPlaceOrder(String sku, String quantity, String country) {
//        return shopApiClient.orders().placeOrder(sku, quantity, country);
//    }
//
//    public void assertOrderPlacementFailed(HttpResponse<String> httpResponse) {
//        shopApiClient.orders().assertOrderPlacementFailed(httpResponse);
//    }
//
//    public String getOrderPlacementErrorMessage(HttpResponse<String> httpResponse) {
//        return shopApiClient.orders().getErrorMessage(httpResponse);
//    }
//
//    @Override
//    public void close() {
//        if (shopApiClient != null) {
//            shopApiClient.close();
//        }
//    }
}

