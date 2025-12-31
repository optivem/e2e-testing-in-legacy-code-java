package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.verifications.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.ViewOrderVerification;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class ThenSuccessBuilder<TVerification extends ResponseVerification<?, UseCaseContext>> extends BaseThenBuilder {
    private final TVerification successVerification;

    public ThenSuccessBuilder(ThenClause thenClause, TVerification successVerification) {
        super(thenClause);
        this.successVerification = successVerification;
    }

    public ThenSuccessBuilder<TVerification> expectOrderNumberPrefix(String prefix) {
        if (successVerification instanceof PlaceOrderVerification verification) {
            verification.orderNumberStartsWith(prefix);
        } else if(successVerification instanceof ViewOrderVerification verification) {
            verification.orderNumberHasPrefix(prefix);
        } else {
            throw new IllegalStateException("expectOrderNumberPrefix is not supported for this verification type");
        }

        return this;
    }
}
