package com.optivem.eshop.systemtest.core.clients.system.ui.pages;

import com.microsoft.playwright.Page;
import com.optivem.eshop.systemtest.core.clients.commons.BasePage;

public class HomePage extends BasePage {

    private static final String SHOP_BUTTON_SELECTOR = "a[href='/shop.html']";
    private static final String ORDER_HISTORY_BUTTON_SELECTOR = "a[href='/order-history.html']";

    public HomePage(Page page, String baseUrl) {
        super(page, baseUrl);
    }

    public NewOrderPage clickNewOrder() {
        click(SHOP_BUTTON_SELECTOR);
        return new NewOrderPage(page, getBaseUrl());
    }

    public OrderHistoryPage clickOrderHistory() {
        click(ORDER_HISTORY_BUTTON_SELECTOR);
        return new OrderHistoryPage(page, getBaseUrl());
    }
}

