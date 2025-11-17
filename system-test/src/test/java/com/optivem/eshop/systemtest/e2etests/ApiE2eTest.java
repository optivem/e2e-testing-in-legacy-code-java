package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.TestConfiguration;
import com.optivem.eshop.systemtest.core.clients.api.ApiClient;
import com.optivem.eshop.systemtest.core.clients.api.dtos.GetOrderResponse;
import com.optivem.eshop.systemtest.core.clients.api.dtos.OrderStatus;
import com.optivem.eshop.systemtest.e2etests.helpers.ErpApiHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ApiE2eTest {

    private static final String BASE_URL = TestConfiguration.getBaseUrl();
    private ApiClient apiClient;
    private HttpClient httpClient; // Still needed for ERP API helper

    @BeforeEach
    void setUp() {
        apiClient = new ApiClient(BASE_URL);
        httpClient = HttpClient.newHttpClient(); // For ERP API helper
    }

    @AfterEach
    void tearDown() {
        if (apiClient != null) {
            apiClient.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    @Test
    void placeOrder_shouldReturnOrderNumber() {
        // Arrange - Set up product in ERP
        var baseSku = "AUTO-PO-100";
        var unitPrice = new BigDecimal("199.99");
        var quantity = 5;

        var sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.orders().placeOrder(sku, String.valueOf(quantity), "US");

        // Assert
        var response = apiClient.orders().assertOrderPlacedSuccessfully(httpResponse);

        // Verify response contains orderNumber
        assertNotNull(response.getOrderNumber(), "Order number should not be null");
        assertTrue(response.getOrderNumber().startsWith("ORD-"), "Order number should start with ORD-");
    }

    @Test
    void getOrder_shouldReturnOrderDetails() throws Exception {
        // Arrange - Set up product in ERP first
        var baseSku = "AUTO-GO-200";
        var unitPrice = new BigDecimal("299.50");
        var quantity = 3;
        var country = "DE";

        var sku = setupProductInErpAndGetSku(baseSku, "Test Laptop", unitPrice);

        // Place order
        var orderNumber = placeOrderAndGetOrderNumber(sku, quantity, country);

        // Act - Get the order details
        var httpResponse = apiClient.orders().viewOrder(orderNumber);

        // Assert
        var getOrderResponse = apiClient.orders().assertOrderViewedSuccessfully(httpResponse);

        // Assert all fields from GetOrderResponse
        assertNotNull(getOrderResponse.getOrderNumber(), "Order number should not be null");
        assertEquals(orderNumber, getOrderResponse.getOrderNumber(), "Order number should match");
        assertEquals(sku, getOrderResponse.getSku(), "SKU should be " + sku);
        assertEquals(quantity, getOrderResponse.getQuantity(), "Quantity should be " + quantity);
        assertEquals(country, getOrderResponse.getCountry(), "Country should be " + country);

        // Assert with concrete values based on known input
        assertEquals(unitPrice, getOrderResponse.getUnitPrice(), "Unit price should be " + unitPrice);

        var expectedOriginalPrice = new BigDecimal("898.50");
        assertEquals(expectedOriginalPrice, getOrderResponse.getOriginalPrice(),
                "Original price should be " + expectedOriginalPrice);

        assertNotNull(getOrderResponse.getStatus(), "Status should not be null");
        assertEquals(OrderStatus.PLACED, getOrderResponse.getStatus(), "Status should be PLACED");
    }

    @Test
    void cancelOrder_shouldSetStatusToCancelled() throws Exception {
        // Arrange - Place an order
        var sku = "HUA-P30";
        var quantity = 2;
        var country = "UK";

        var orderNumber = placeOrderAndGetOrderNumber(sku, quantity, country);

        assertNotNull(orderNumber, "Order number should not be null");

        // Act - Cancel the order
        var httpResponse = apiClient.orders().cancelOrder(orderNumber);

        // Assert - Verify cancel response
        apiClient.orders().assertOrderCancelledSuccessfully(httpResponse);

        // Verify order status is CANCELLED
        var orderDetails = getOrderDetails(orderNumber);
        assertEquals(OrderStatus.CANCELLED, orderDetails.getStatus(), "Order status should be CANCELLED");
    }


    @Test
    void shouldRejectOrderWithNonExistentSku() throws Exception {
        // Arrange
        var sku = "NON-EXISTENT-SKU-12345";
        var quantity = "5";
        var country = "US";

        // Act
        var httpResponse = apiClient.orders().placeOrder(sku, quantity, country);

        // Assert
        apiClient.orders().assertOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.orders().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Product does not exist for SKU"),
                "Error message should contain 'Product does not exist for SKU'. Actual: " + errorMessage);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() throws Exception {
        // Arrange - Set up product in ERP first
        var baseSku = "AUTO-NQ-400";
        var unitPrice = new BigDecimal("99.99");

        var sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.orders().placeOrder(sku, "-5", "US");

        // Assert
        apiClient.orders().assertOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.orders().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Quantity must be positive"),
                "Error message should contain 'Quantity must be positive'. Actual: " + errorMessage);
    }

    private static Stream<Arguments> provideEmptySkuValues() {
        return Stream.of(
                Arguments.of((String) null),  // Null value
                Arguments.of(""),             // Empty string
                Arguments.of("   ")           // Whitespace string
        );
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
    void shouldRejectOrderWithEmptyQuantity(String quantityValue) throws Exception {
        // Arrange - Set up product in ERP first
        var baseSku = "AUTO-EQ-500";
        var unitPrice = new BigDecimal("150.00");

        var sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.orders().placeOrder(sku, quantityValue, "US");

        // Assert
        apiClient.orders().assertOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.orders().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Quantity must not be empty"),
                "Error message should be 'Quantity must not be empty'. Actual: " + errorMessage);
    }

    private static Stream<Arguments> provideNonIntegerQuantityValues() {
        return Stream.of(
                Arguments.of("3.5"),    // Decimal value
                Arguments.of("lala")    // String value
        );
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
    void shouldRejectOrderWithEmptyCountry(String countryValue) throws Exception {
        // Arrange - Set up product in ERP first and get unique SKU
        var baseSku = "AUTO-EC-700";
        var unitPrice = new BigDecimal("225.00");

        var sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.orders().placeOrder(sku, "5", countryValue);

        // Assert
        apiClient.orders().assertOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.orders().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty'. Actual: " + errorMessage);
    }


    // Helper method that returns the unique SKU for use in tests
    private String setupProductInErpAndGetSku(String baseSku, String title, BigDecimal price) {
        return ErpApiHelper.setupProductInErp(httpClient, baseSku, title, price);
    }

    private String placeOrderAndGetOrderNumber(String sku, int quantity, String country) {
        var httpResponse = apiClient.orders().placeOrder(sku, String.valueOf(quantity), country);
        var placeOrderResponse = apiClient.orders().assertOrderPlacedSuccessfully(httpResponse);
        return placeOrderResponse.getOrderNumber();
    }

    private GetOrderResponse getOrderDetails(String orderNumber) {
        var httpResponse = apiClient.orders().viewOrder(orderNumber);
        return apiClient.orders().assertOrderViewedSuccessfully(httpResponse);
    }
}
