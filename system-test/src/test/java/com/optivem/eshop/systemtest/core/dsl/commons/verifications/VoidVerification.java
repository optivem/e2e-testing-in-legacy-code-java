package com.optivem.eshop.systemtest.core.dsl.commons.verifications;

import com.optivem.eshop.systemtest.core.dsl.commons.verifications.base.BaseSuccessVerification;
import com.optivem.eshop.systemtest.core.dsl.commons.context.Context;

@SuppressWarnings("UnusedReturnValue")
public class VoidVerification extends BaseSuccessVerification<Void> {

    public VoidVerification(Void response, Context context) {
        super(response, context);
    }
}

