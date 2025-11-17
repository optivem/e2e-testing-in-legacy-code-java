package com.optivem.eshop.systemtest.core.clients.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.optivem.eshop.systemtest.TestConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderHistoryPage extends BasePage {

    private static final String ORDER_NUMBER_INPUT_SELECTOR = "[aria-label='Order Number']";
    private static final String SEARCH_BUTTON_SELECTOR = "[aria-label='Search']";
    private static final String CONFIRMATION_MESSAGE_SELECTOR = "[role='alert']";
    private static final String ORDER_NUMBER_OUTPUT_SELECTOR = "[aria-label='Display Order Number']";
    private static final String PRODUCT_ID_OUTPUT_SELECTOR = "[aria-label='Display Product ID']";
    private static final String COUNTRY_OUTPUT_SELECTOR = "[aria-label='Display Country']";
    private static final String QUANTITY_OUTPUT_SELECTOR = "[aria-label='Display Quantity']";
    private static final String UNIT_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Unit Price']";
    private static final String ORIGINAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Original Price']";
    private static final String DISCOUNT_RATE_OUTPUT_SELECTOR = "[aria-label='Display Discount Rate']";
    private static final String DISCOUNT_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Discount Amount']";
    private static final String SUBTOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Subtotal Price']";
    private static final String TAX_RATE_OUTPUT_SELECTOR = "[aria-label='Display Tax Rate']";
    private static final String TAX_AMOUNT_OUTPUT_SELECTOR = "[aria-label='Display Tax Amount']";
    private static final String TOTAL_PRICE_OUTPUT_SELECTOR = "[aria-label='Display Total Price']";
    private static final String STATUS_OUTPUT_SELECTOR = "[aria-label='Display Status']";
    private static final String CANCEL_ORDER_OUTPUT_SELECTOR = "[aria-label='Cancel Order']";

    private static final String ORDER_DETAILS_HEADING_TEXT = "Order Details";

    public OrderHistoryPage(Page page, String baseUrl) {
        super(page, baseUrl);
    }

    public void inputOrderNumber(String orderNumber) {
        fill(ORDER_NUMBER_INPUT_SELECTOR, orderNumber);
    }

    public void clickSearch() {
        click(SEARCH_BUTTON_SELECTOR);
    }

    public void waitForOrderDetails() {
        var orderDetailsText = readTextContent(CONFIRMATION_MESSAGE_SELECTOR);
        assertTrue(orderDetailsText.contains(ORDER_DETAILS_HEADING_TEXT), "Should display order details heading");
    }

    public String getOrderNumber() {
        return readInputValue(ORDER_NUMBER_OUTPUT_SELECTOR);
    }

    public String getProductId() {
        return readInputValue(PRODUCT_ID_OUTPUT_SELECTOR);
    }

    public String getCountry() {
        return readInputValue(COUNTRY_OUTPUT_SELECTOR);
    }

    public String getQuantity() {
        return readInputValue(QUANTITY_OUTPUT_SELECTOR);
    }

    public String getUnitPrice() {
        return readInputValue(UNIT_PRICE_OUTPUT_SELECTOR);
    }

    public String getOriginalPrice() {
        return readInputValue(ORIGINAL_PRICE_OUTPUT_SELECTOR);
    }

    public String getDiscountRate() {
        return readInputValue(DISCOUNT_RATE_OUTPUT_SELECTOR);
    }

    public String getDiscountAmount() {
        return readInputValue(DISCOUNT_AMOUNT_OUTPUT_SELECTOR);
    }

    public String getSubtotalPrice() {
        return readInputValue(SUBTOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public String getTaxRate() {
        return readInputValue(TAX_RATE_OUTPUT_SELECTOR);
    }

    public String getTaxAmount() {
        return readInputValue(TAX_AMOUNT_OUTPUT_SELECTOR);
    }

    public String getTotalPrice() {
        return readInputValue(TOTAL_PRICE_OUTPUT_SELECTOR);
    }

    public String getStatus() {
        return readInputValue(STATUS_OUTPUT_SELECTOR);
    }

    public void clickCancelOrder() {
        click(CANCEL_ORDER_OUTPUT_SELECTOR);
        waitForHidden(CANCEL_ORDER_OUTPUT_SELECTOR);
    }

    public void assertCancelButtonNotVisible() {
        assertTrue(isHidden(CANCEL_ORDER_OUTPUT_SELECTOR), "Cancel Order button should not be visible");
    }
}

