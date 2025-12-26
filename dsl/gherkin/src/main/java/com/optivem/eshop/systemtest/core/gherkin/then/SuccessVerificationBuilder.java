package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.verifications.PlaceOrderVerification;
import com.optivem.eshop.systemtest.core.shop.dsl.verifications.ViewOrderVerification;
import com.optivem.testing.dsl.ResponseVerification;
import com.optivem.testing.dsl.UseCaseContext;

public class SuccessVerificationBuilder<TVerification extends ResponseVerification<?, UseCaseContext>> {
    private final ThenClause thenClause;
    private final TVerification successVerification;

    public SuccessVerificationBuilder(ThenClause thenClause, TVerification successVerification) {
        this.thenClause = thenClause;
        this.successVerification = successVerification;
    }

    public SuccessVerificationBuilder<TVerification> expectOrderNumberPrefix(String prefix) {
        if (successVerification instanceof PlaceOrderVerification verification) {
            verification.orderNumberStartsWith(prefix);
        } else if(successVerification instanceof ViewOrderVerification verification) {
            verification.orderNumberHasPrefix(prefix);
        } else {
            throw new IllegalStateException("expectOrderNumberPrefix is not supported for this verification type");
        }

        return this;
    }

    public ThenClause and() {
        return thenClause;
    }
}
