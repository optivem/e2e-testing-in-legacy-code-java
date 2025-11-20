package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopUiDriver implements ShopDriver {
    private final ShopUiClient client;
    private final HashMap<String, String> orderNumbers;

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

    public ShopUiDriver(String baseUrl) {
        this.client = new ShopUiClient(baseUrl);
        this.orderNumbers = new HashMap<>();
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
    public void placeOrder(String orderNumberAlias, String productId, String quantity, String country) {
        ensureOnNewOrderPage();
        newOrderPage.inputProductId(productId);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        var orderNumberOptional = newOrderPage.getOrderNumber();

        orderNumberOptional.ifPresent(orderNumber -> registerOrderNumber(orderNumberAlias, orderNumber));
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
        var orderNumber = getOrderNumber(orderNumberAlias);
        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();
    }

    @Override
    public void confirmOrderDetails(String orderNumberAlias, String productId, String quantity, String status) {
        // TODO: VC: If on new order page, we then need to confirm the first original price before going to view the details
//        var originalPrice = newOrderPage.extractOriginalPrice();
//
//        assertEquals(549.75, originalPrice, 0.01, "Original price should be $549.75 (5 × $109.95)");

        viewOrderDetails(orderNumberAlias);

        var orderNumber = getOrderNumber(orderNumberAlias);
        var displayOrderNumber = orderHistoryPage.getOrderNumber();
        assertEquals(orderNumber, displayOrderNumber, "Should display the order number: " + orderNumber);

        var displayProductId = orderHistoryPage.getProductId();
        assertEquals(productId, displayProductId, "Should display product ID: " + productId);

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

    private void registerOrderNumber(String orderNumberAlias, String orderNumber) {
        if(orderNumbers.containsKey(orderNumberAlias)) {
            throw new IllegalStateException("Order number alias already registered: " + orderNumberAlias);
        }

        orderNumbers.put(orderNumberAlias, orderNumber);
    }

    private String getOrderNumber(String orderNumberAlias) {
        var orderNumber = orderNumbers.get(orderNumberAlias);
        if(orderNumber == null) {
            throw new IllegalStateException("Order number alias not registered: " + orderNumberAlias);
        }

        return orderNumber;
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

