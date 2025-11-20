package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.context.Context;
import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;
    private final Context context;

    private HomePage homePage;
    private NewOrderPage newOrderPage;
    private OrderHistoryPage orderHistoryPage;

    private Pages currentPage;

    private static enum Pages {
        NONE,
        HOME,
        NEW_ORDER,
        ORDER_HISTORY
    }

    public ShopUiDriver(String baseUrl, Context context) {
        this.client = new ShopUiClient(baseUrl);
        this.context = context;
    }

    @Override
    public void goToShop() {
        homePage = client.openHomePage();
        client.assertHomePageLoaded();
        currentPage = Pages.HOME;

        newOrderPage = homePage.clickNewOrder();
    }

    private void ensureOnNewOrderPage() {
        if(currentPage != Pages.NEW_ORDER) {
            homePage = client.openHomePage();
            newOrderPage = homePage.clickNewOrder();
            currentPage = Pages.NEW_ORDER;
        }
    }

    private void ensureOnOrderHistoryPage() {
        if(currentPage != Pages.ORDER_HISTORY) {
            homePage = client.openHomePage();
            orderHistoryPage = homePage.clickOrderHistory();
            currentPage = Pages.ORDER_HISTORY;
        }
    }

    @Override
    public void placeOrder(String orderNumberAlias, String skuAlias, String quantity, String country) {

        var skueValue = context.params().alias(skuAlias);

        ensureOnNewOrderPage();
        newOrderPage.inputProductId(skueValue);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        var orderNumberValue = newOrderPage.getOrderNumber();

        orderNumberValue.ifPresent(v -> context.results().alias(orderNumberAlias, v));
    }

    @Override
    public void confirmOrderPlaced(String orderNumberAlias, String prefix) {
        newOrderPage.assertConfirmationMessageShown();
        assertTrue(newOrderPage.getOrderNumber().isPresent(), "Order number should be present after placing order");
        assertTrue(newOrderPage.getOriginalPrice().isPresent(), "Original price should be present after placing order");
        assertTrue(newOrderPage.getOriginalPrice().get().compareTo(BigDecimal.ZERO) > 0, "Original price should be positive after placing order");

        var displayOrderNumber = newOrderPage.getOrderNumber();
        assertTrue(displayOrderNumber.isPresent(), "Order number should be present");
        assertTrue(displayOrderNumber.get().startsWith(prefix), "Order number should start with prefix: " + prefix);
    }

    @Override
    public void viewOrderDetails(String orderNumberAlias) {
        ensureOnOrderHistoryPage();
        var orderNumberValue = context.results().alias(orderNumberAlias);
        orderHistoryPage.inputOrderNumber(orderNumberValue);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();
    }

    @Override
    public void confirmOrderDetails(String orderNumberAlias, String skuAlias, String quantity, String status) {
        // TODO: VC: If on new order page, we then need to confirm the first original price before going to view the details
//        var originalPrice = newOrderPage.extractOriginalPrice();
//
//        assertEquals(549.75, originalPrice, 0.01, "Original price should be $549.75 (5 × $109.95)");

        var orderNumberValue = context.results().alias(orderNumberAlias);
        var skuValue = context.params().alias(skuAlias);

        viewOrderDetails(orderNumberAlias);

        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        assertEquals(orderNumberValue, displayOrderNumber, "Should display the order number: " + orderNumberValue);

        var displayProductId = orderHistoryPage.getProductId();
        assertEquals(skuValue, displayProductId, "Should display product ID: " + skuValue);

        var displayQuantity = orderHistoryPage.getQuantity();
        assertEquals(quantity, displayQuantity, "Should display quantity: " + quantity);

        var displayUnitPrice = orderHistoryPage.getUnitPrice();
        assertTrue(displayUnitPrice.compareTo(BigDecimal.ZERO) > 0, "Unit price should be positive");

        var displayOriginalPrice = orderHistoryPage.getOriginalPrice();
        assertTrue(displayOriginalPrice.compareTo(BigDecimal.ZERO) > 0, "Original price should be positive");

        // TODO: VJ: Should confirm the various other calculated prices too
    }

    @Override
    public void cancelOrder(String orderNumberAlias) {
        viewOrderDetails(orderNumberAlias);
        orderHistoryPage.clickCancelOrder();
    }

    @Override
    public void confirmOrderCancelled(String orderNumberAlias) {
        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        assertEquals("CANCELLED", displayStatusAfterCancel, "Status should be CANCELLED after cancellation");
        orderHistoryPage.assertCancelButtonNotVisible();
    }

    // TODO: VJ: This is duplicate
    @Override
    public void confirmOrderStatusIsCancelled(String orderNumberAlias) {
        var displayStatusAfterCancel = orderHistoryPage.getStatus();
        assertEquals("CANCELLED", displayStatusAfterCancel, "Status should be CANCELLED after cancellation");
        orderHistoryPage.assertCancelButtonNotVisible();
    }

    @Override
    public void confirmOrderNumberGeneratedWithPrefix(String orderNumberAlias, String expectedPrefix) {
        // NOTE: VJ: If we are on order creation page, then check order number generated correctly

        var orderNumberValue = context.results().alias(orderNumberAlias);

        viewOrderDetails(orderNumberAlias);

        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        assertEquals(orderNumberValue, displayOrderNumber, "Should display the order number: " + orderNumberValue);

        assertThat(displayOrderNumber)
                .withFailMessage("Order number should start with prefix: " + expectedPrefix)
                .startsWith(expectedPrefix);
    }

    @Override
    public void close() {
        client.close();
    }

//    private final ShopUiClient shopUiClient;
//
//    public ShopUiDriver(ShopUiClient shopUiClient) {
//        this.shopUiClient = shopUiClient;
//    }
//
//    public HomePage openHomePage() {
//        return shopUiClient.openHomePage();
//    }
//
//    public void assertHomePageLoaded() {
//        shopUiClient.openHomePage();
//        shopUiClient.assertHomePageLoaded();
//    }
//
//    public String placeOrder(String sku, String quantity, String country) {
//        var homePage = shopUiClient.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity(quantity);
//        newOrderPage.inputCountry(country);
//        newOrderPage.clickPlaceOrder();
//
//        return newOrderPage.extractOrderNumber();
//    }
//
//    public OrderHistoryPage viewOrderDetails(String orderNumber) {
//        var homePage = shopUiClient.openHomePage();
//        var orderHistoryPage = homePage.clickOrderHistory();
//
//        orderHistoryPage.inputOrderNumber(orderNumber);
//        orderHistoryPage.clickSearch();
//        orderHistoryPage.waitForOrderDetails();
//
//        return orderHistoryPage;
//    }
//
//    public void cancelOrder(String orderNumber) {
//        var orderHistoryPage = viewOrderDetails(orderNumber);
//        orderHistoryPage.clickCancelOrder();
//    }
//
//    public NewOrderPage attemptPlaceOrder(String sku, String quantity, String country) {
//        var homePage = shopUiClient.openHomePage();
//        var newOrderPage = homePage.clickNewOrder();
//
//        newOrderPage.inputProductId(sku);
//        newOrderPage.inputQuantity(quantity);
//        newOrderPage.inputCountry(country);
//        newOrderPage.clickPlaceOrder();
//
//        return newOrderPage;
//    }
//
//    @Override
//    public void close() {
//        if (shopUiClient != null) {
//            shopUiClient.close();
//        }
//    }
}

