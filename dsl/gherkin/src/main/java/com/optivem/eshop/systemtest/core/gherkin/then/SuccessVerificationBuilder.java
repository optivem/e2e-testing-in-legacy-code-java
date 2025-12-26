package com.optivem.eshop.systemtest.core.gherkin.then;

import com.optivem.eshop.systemtest.core.shop.dsl.verifications.PlaceOrderVerification;
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
        if (successVerification instanceof PlaceOrderVerification) {
            ((PlaceOrderVerification) successVerification).orderNumberStartsWith(prefix);
        }
        return this;
    }

    public ThenClause and() {
        return thenClause;
    }
}
