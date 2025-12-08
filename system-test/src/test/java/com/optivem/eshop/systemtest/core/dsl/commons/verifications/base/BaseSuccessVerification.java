package com.optivem.eshop.systemtest.core.dsl.commons.verifications.base;

import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;

public abstract class BaseSuccessVerification<TResponse> {
    protected final TResponse response;
    protected final Context context;

    protected BaseSuccessVerification(TResponse response, Context context) {
        this.response = response;
        this.context = context;
    }
}

