package com.optivem.eshop.systemtest.core.drivers.system;

import com.optivem.eshop.systemtest.core.clients.system.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.HomePage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.NewOrderPage;
import com.optivem.eshop.systemtest.core.clients.system.ui.pages.OrderHistoryPage;

/**
 * Driver for Shop UI operations.
 * Encapsulates high-level business operations using the Shop UI client.
 */
public class ShopUiDriver implements AutoCloseable {

    private final ShopUiClient shopUiClient;

    public ShopUiDriver(ShopUiClient shopUiClient) {
        this.shopUiClient = shopUiClient;
    }

    /**
     * Opens the home page of the shop.
     *
     * @return the home page
     */
    public HomePage openHomePage() {
        return shopUiClient.openHomePage();
    }

    /**
     * Places an order through the UI and returns the order number.
     *
     * @param sku      the product SKU
     * @param quantity the quantity to order
     * @param country  the country code
     * @return the order number
     */
    public String placeOrder(String sku, String quantity, String country) {
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        newOrderPage.inputProductId(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        return newOrderPage.extractOrderNumber();
    }

    /**
     * Views the details of an order in the UI.
     *
     * @param orderNumber the order number
     * @return the order history page with the order details
     */
    public OrderHistoryPage viewOrderDetails(String orderNumber) {
        var homePage = shopUiClient.openHomePage();
        var orderHistoryPage = homePage.clickOrderHistory();

        orderHistoryPage.inputOrderNumber(orderNumber);
        orderHistoryPage.clickSearch();
        orderHistoryPage.waitForOrderDetails();

        return orderHistoryPage;
    }

    /**
     * Cancels an order through the UI.
     *
     * @param orderNumber the order number to cancel
     */
    public void cancelOrder(String orderNumber) {
        var orderHistoryPage = viewOrderDetails(orderNumber);
        orderHistoryPage.clickCancelOrder();
    }

    /**
     * Attempts to place an order with potentially invalid data.
     *
     * @param sku      the product SKU
     * @param quantity the quantity
     * @param country  the country code
     * @return the new order page to check for errors
     */
    public NewOrderPage attemptPlaceOrder(String sku, String quantity, String country) {
        var homePage = shopUiClient.openHomePage();
        var newOrderPage = homePage.clickNewOrder();

        newOrderPage.inputProductId(sku);
        newOrderPage.inputQuantity(quantity);
        newOrderPage.inputCountry(country);
        newOrderPage.clickPlaceOrder();

        return newOrderPage;
    }

    @Override
    public void close() {
        if (shopUiClient != null) {
            shopUiClient.close();
        }
    }
}

