package com.optivem.eshop.systemtest.core.drivers.system;

public interface ShopDriver extends AutoCloseable {
    void goToShop();

    void placeOrder(String orderNumberAlias, String productId, String quantity, String country);

    void confirmOrderPlaced(String orderNumberAlias, String prefix);

    void viewOrderDetails(String orderNumberAlias);

    void confirmOrderDetails(String orderNumberAlias, String productId, String quantity, String status);

    void cancelOrder(String orderNumberAlias);

    void confirmOrderCancelled(String orderNumberAlias);

    void confirmOrderStatusIsCancelled(String orderNumberAlias);

    void confirmOrderNumberGeneratedWithPrefix(String orderNumberAlias, String expectedPrefix);
}
