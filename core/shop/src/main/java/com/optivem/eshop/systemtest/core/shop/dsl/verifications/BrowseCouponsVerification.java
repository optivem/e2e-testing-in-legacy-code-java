package com.optivem.eshop.systemtest.core.shop.dsl.verifications;

import com.optivem.eshop.systemtest.core.shop.commons.dtos.coupons.BrowseCouponsResponse;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class BrowseCouponsVerification extends ResponseVerification<BrowseCouponsResponse, UseCaseContext> {

    public BrowseCouponsVerification(BrowseCouponsResponse response, UseCaseContext context) {
        super(response, context);
    }
}
