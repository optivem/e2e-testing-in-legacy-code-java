package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

public class OrderHistoryPage extends BasePage {
    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Search']";

    public OrderHistoryPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputOrderNumber(String orderNumber) {
        pageClient.fill(ORDER_NUMBER_INPUT_SELECTOR, orderNumber);
    }

    public void clickSearch() {
        pageClient.click(SEARCH_BUTTON_SELECTOR);
    }

    public boolean isOrderListed(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        return pageClient.exists(rowSelector);
    }

    public OrderDetailsPage clickViewOrderDetails(String orderNumber) {
        var rowSelector = getRowSelector(orderNumber);
        var viewDetailsLinkSelector = rowSelector + "//a[@aria-label='View Details']";
        pageClient.click(viewDetailsLinkSelector);
        return new OrderDetailsPage(pageClient);
    }

    private String getRowSelector(String orderNumber) {
        return String.format("//tr[td[@aria-label='Display Order Number' and text()='%s']]", orderNumber);
    }


}
