package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.clients.system.api.dtos.OrderStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApiE2eTest extends BaseE2eTest {

    @Test
    void placeOrder_shouldReturnOrderNumber() {
        var baseSku = "AUTO-PO-100";
        var unitPrice = new BigDecimal("199.99");
        var quantity = 5;

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var orderNumber = shopApiDriver.placeOrder(sku, quantity, "US");

        assertNotNull(orderNumber, "Order number should not be null");
        assertTrue(orderNumber.startsWith("ORD-"), "Order number should start with ORD-");
    }

    @Test
    void getOrder_shouldReturnOrderDetails() {
        var baseSku = "AUTO-GO-200";
        var unitPrice = new BigDecimal("299.50");
        var quantity = 3;
        var country = "DE";

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var orderNumber = shopApiDriver.placeOrder(sku, quantity, country);

        var getOrderResponse = shopApiDriver.getOrderDetails(orderNumber);

        assertNotNull(getOrderResponse.getOrderNumber(), "Order number should not be null");
        assertEquals(orderNumber, getOrderResponse.getOrderNumber(), "Order number should match");
        assertEquals(sku, getOrderResponse.getSku(), "SKU should be " + sku);
        assertEquals(quantity, getOrderResponse.getQuantity(), "Quantity should be " + quantity);
        assertEquals(country, getOrderResponse.getCountry(), "Country should be " + country);

        assertEquals(unitPrice, getOrderResponse.getUnitPrice(), "Unit price should be " + unitPrice);

        var expectedOriginalPrice = new BigDecimal("898.50");
        assertEquals(expectedOriginalPrice, getOrderResponse.getOriginalPrice(),
                "Original price should be " + expectedOriginalPrice);

        assertNotNull(getOrderResponse.getStatus(), "Status should not be null");
        assertEquals(OrderStatus.PLACED, getOrderResponse.getStatus(), "Status should be PLACED");
    }

    @Test
    void cancelOrder_shouldSetStatusToCancelled() {
        var sku = "HUA-P30";
        var quantity = 2;
        var country = "UK";

        var orderNumber = shopApiDriver.placeOrder(sku, quantity, country);

        assertNotNull(orderNumber, "Order number should not be null");

        shopApiDriver.cancelOrder(orderNumber);

        var orderDetails = shopApiDriver.getOrderDetails(orderNumber);
        assertEquals(OrderStatus.CANCELLED, orderDetails.getStatus(), "Order status should be CANCELLED");
    }


    @Test
    void shouldRejectOrderWithNonExistentSku() {
        var sku = "NON-EXISTENT-SKU-12345";
        var quantity = "5";
        var country = "US";

        var httpResponse = shopApiDriver.attemptPlaceOrder(sku, quantity, country);

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Product does not exist for SKU"),
                "Error message should contain 'Product does not exist for SKU'. Actual: " + errorMessage);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() {
        var baseSku = "AUTO-NQ-400";
        var unitPrice = new BigDecimal("99.99");

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var httpResponse = shopApiDriver.attemptPlaceOrder(sku, "-5", "US");

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Quantity must be positive"),
                "Error message should contain 'Quantity must be positive'. Actual: " + errorMessage);
    }

    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of("   ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptySkuValues")
    void shouldRejectOrderWithEmptySku(String skuValue) {
        var httpResponse = shopApiDriver.attemptPlaceOrder(skuValue, "5", "US");

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("SKU must not be empty"),
                "Error message should be 'SKU must not be empty'. Actual: " + errorMessage);
    }


    private static Stream<Arguments> provideEmptyQuantityValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyQuantityValues")
    void shouldRejectOrderWithEmptyQuantity(String quantityValue) {
        var baseSku = "AUTO-EQ-500";
        var unitPrice = new BigDecimal("150.00");

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var httpResponse = shopApiDriver.attemptPlaceOrder(sku, quantityValue, "US");

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Quantity must not be empty"),
                "Error message should be 'Quantity must not be empty'. Actual: " + errorMessage);
    }

    private static Stream<Arguments> provideNonIntegerQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),    // Decimal value
                Arguments.of("lala")    // String value
        );
    }

    @ParameterizedTest
    @MethodSource("provideNonIntegerQuantityValues")
    void shouldRejectOrderWithNonIntegerQuantity(String quantityValue) {
        var baseSku = "AUTO-NIQ-600";
        var unitPrice = new BigDecimal("175.00");

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var httpResponse = shopApiDriver.attemptPlaceOrder(sku, quantityValue, "US");

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Quantity must be an integer"),
                "Error message should contain 'Quantity must be an integer'. Actual: " + errorMessage);
    }

    private static Stream<Arguments> provideEmptyCountryValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCountryValues")
    void shouldRejectOrderWithEmptyCountry(String countryValue) {
        var baseSku = "AUTO-EC-700";
        var unitPrice = new BigDecimal("225.00");

        var sku = erpApiDriver.createProduct(baseSku, unitPrice);

        var httpResponse = shopApiDriver.attemptPlaceOrder(sku, "5", countryValue);

        shopApiDriver.assertOrderPlacementFailed(httpResponse);

        var errorMessage = shopApiDriver.getOrderPlacementErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty'. Actual: " + errorMessage);
    }

}
