package com.optivem.eshop.systemtest.core.shop.client.ui.pages;

import com.optivem.playwright.PageClient;

public class CouponManagementPage extends BasePage {

    private static final String COUPON_CODE_INPUT_SELECTOR = "[aria-label=\"Coupon Code\"]";
    private static final String DISCOUNT_RATE_INPUT_SELECTOR = "[aria-label=\"Discount Rate\"]";
    private static final String PUBLISH_COUPON_BUTTON_SELECTOR = "[aria-label=\"Create Coupon\"]";

    public CouponManagementPage(PageClient pageClient) {
        super(pageClient);
    }

    public void inputCouponCode(String couponCode) {
        pageClient.fill(COUPON_CODE_INPUT_SELECTOR, couponCode);
    }

    public void inputDiscountRate(String discountRate) {
        pageClient.fill(DISCOUNT_RATE_INPUT_SELECTOR, discountRate);
    }

    public void clickPublishCoupon() {
        pageClient.click(PUBLISH_COUPON_BUTTON_SELECTOR);
    }

    public boolean hasSuccessCouponNotification() {
        return hasSuccessNotification();
    }
}

