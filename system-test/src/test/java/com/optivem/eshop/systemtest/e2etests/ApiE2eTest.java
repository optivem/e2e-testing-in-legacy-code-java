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
    void placeOrder_shouldReturnOrderNumber() throws Exception {
        // Arrange - Set up product in ERP
        String baseSku = "AUTO-PO-100";
        BigDecimal unitPrice = new BigDecimal("199.99");
        int quantity = 5;

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.getOrderController().placeOrder(sku, String.valueOf(quantity), "US");

        // Assert
        var response = apiClient.getOrderController().confirmOrderPlacedSuccessfully(httpResponse);

        // Verify response contains orderNumber
        assertNotNull(response.getOrderNumber(), "Order number should not be null");
        assertTrue(response.getOrderNumber().startsWith("ORD-"), "Order number should start with ORD-");
    }

    @Test
    void getOrder_shouldReturnOrderDetails() throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-GO-200";
        BigDecimal unitPrice = new BigDecimal("299.50");
        int quantity = 3;
        String country = "DE";

        String sku = setupProductInErpAndGetSku(baseSku, "Test Laptop", unitPrice);

        // Place order
        var orderNumber = placeOrderAndGetOrderNumber(sku, quantity, country);

        // Act - Get the order details
        var httpResponse = apiClient.getOrderController().viewOrder(orderNumber);

        // Assert
        var getOrderResponse = apiClient.getOrderController().confirmOrderViewedSuccessfully(httpResponse);

        // Assert all fields from GetOrderResponse
        assertNotNull(getOrderResponse.getOrderNumber(), "Order number should not be null");
        assertEquals(orderNumber, getOrderResponse.getOrderNumber(), "Order number should match");
        assertEquals(sku, getOrderResponse.getSku(), "SKU should be " + sku);
        assertEquals(quantity, getOrderResponse.getQuantity(), "Quantity should be " + quantity);
        assertEquals(country, getOrderResponse.getCountry(), "Country should be " + country);

        // Assert with concrete values based on known input
        assertEquals(unitPrice, getOrderResponse.getUnitPrice(), "Unit price should be " + unitPrice);

        BigDecimal expectedOriginalPrice = new BigDecimal("898.50");
        assertEquals(expectedOriginalPrice, getOrderResponse.getOriginalPrice(),
                "Original price should be " + expectedOriginalPrice);

        assertNotNull(getOrderResponse.getStatus(), "Status should not be null");
        assertEquals(OrderStatus.PLACED, getOrderResponse.getStatus(), "Status should be PLACED");
    }

    @Test
    void cancelOrder_shouldSetStatusToCancelled() throws Exception {
        // Arrange - Place an order
        String sku = "HUA-P30";
        int quantity = 2;
        String country = "UK";

        var orderNumber = placeOrderAndGetOrderNumber(sku, quantity, country);

        assertNotNull(orderNumber, "Order number should not be null");

        // Act - Cancel the order
        var httpResponse = apiClient.getOrderController().cancelOrder(orderNumber);

        // Assert - Verify cancel response
        apiClient.getOrderController().confirmOrderCancelledSuccessfully(httpResponse);

        // Verify order status is CANCELLED
        var orderDetails = getOrderDetails(orderNumber);
        assertEquals(OrderStatus.CANCELLED, orderDetails.getStatus(), "Order status should be CANCELLED");
    }


    @Test
    void shouldRejectOrderWithNonExistentSku() throws Exception {
        // Arrange
        String sku = "NON-EXISTENT-SKU-12345";
        String quantity = "5";
        String country = "US";

        // Act
        var httpResponse = apiClient.getOrderController().placeOrder(sku, quantity, country);

        // Assert
        apiClient.getOrderController().confirmOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.getOrderController().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Product does not exist for SKU"),
                "Error message should contain 'Product does not exist for SKU'. Actual: " + errorMessage);
    }

    @Test
    void shouldRejectOrderWithNegativeQuantity() throws Exception {
        // Arrange - Set up product in ERP first
        String baseSku = "AUTO-NQ-400";
        BigDecimal unitPrice = new BigDecimal("99.99");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.getOrderController().placeOrder(sku, "-5", "US");

        // Assert
        apiClient.getOrderController().confirmOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.getOrderController().getErrorMessage(httpResponse);
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
        String baseSku = "AUTO-EQ-500";
        BigDecimal unitPrice = new BigDecimal("150.00");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.getOrderController().placeOrder(sku, quantityValue, "US");

        // Assert
        apiClient.getOrderController().confirmOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.getOrderController().getErrorMessage(httpResponse);
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
        String baseSku = "AUTO-EC-700";
        BigDecimal unitPrice = new BigDecimal("225.00");

        String sku = setupProductInErpAndGetSku(baseSku, "Test Product", unitPrice);

        // Act
        var httpResponse = apiClient.getOrderController().placeOrder(sku, "5", countryValue);

        // Assert
        apiClient.getOrderController().confirmOrderPlacementFailed(httpResponse);

        var errorMessage = apiClient.getOrderController().getErrorMessage(httpResponse);
        assertTrue(errorMessage.contains("Country must not be empty"),
                "Error message should be 'Country must not be empty'. Actual: " + errorMessage);
    }


    // Helper method that returns the unique SKU for use in tests
    private String setupProductInErpAndGetSku(String baseSku, String title, BigDecimal price) throws Exception {
        return ErpApiHelper.setupProductInErp(httpClient, baseSku, title, price);
    }

    private String placeOrderAndGetOrderNumber(String sku, int quantity, String country) {
        var httpResponse = apiClient.getOrderController().placeOrder(sku, String.valueOf(quantity), country);
        var placeOrderResponse = apiClient.getOrderController().confirmOrderPlacedSuccessfully(httpResponse);
        return placeOrderResponse.getOrderNumber();
    }

    private GetOrderResponse getOrderDetails(String orderNumber) {
        var httpResponse = apiClient.getOrderController().viewOrder(orderNumber);
        return apiClient.getOrderController().confirmOrderViewedSuccessfully(httpResponse);
    }
}