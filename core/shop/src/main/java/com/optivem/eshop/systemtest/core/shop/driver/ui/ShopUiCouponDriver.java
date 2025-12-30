package com.optivem.eshop.systemtest.core.shop.driver.ui;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.PublishCouponRequest;
import com.optivem.eshop.systemtest.core.shop.commons.dtos.error.SystemError;
import com.optivem.eshop.systemtest.core.shop.driver.CouponDriver;
import com.optivem.lang.Result;

public class ShopUiCouponDriver implements CouponDriver {
    @Override
    public Result<Void, SystemError> publishCoupon(PublishCouponRequest request) {
        throw new UnsupportedOperationException("publishCoupon not implemented yet");
    }

    @Override
    public Result<BrowseCouponsResponse, SystemError> browseCoupons(BrowseCouponsRequest request) {
        throw new UnsupportedOperationException("browseCoupons not implemented yet");
    }
}

