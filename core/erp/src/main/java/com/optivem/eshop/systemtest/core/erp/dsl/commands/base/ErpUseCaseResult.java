package com.optivem.eshop.systemtest.core.erp.dsl.commands.base;

import com.optivem.eshop.systemtest.core.commons.dsl.ErrorFailureVerification;
import com.optivem.eshop.systemtest.core.commons.error.Error;
import com.optivem.eshop.systemtest.core.erp.driver.dtos.error.ErpErrorResponse;
import com.optivem.lang.Result;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.testing.dsl.UseCaseResult;
import com.optivem.testing.dsl.ResponseVerification;

import java.util.function.BiFunction;

public class ErpUseCaseResult<TSuccessResponse, TSuccessVerification extends ResponseVerification<TSuccessResponse, UseCaseContext>>
        extends UseCaseResult<TSuccessResponse, ErpErrorResponse, UseCaseContext, TSuccessVerification, ErpErrorVerification> {

    public ErpUseCaseResult(
            Result<TSuccessResponse, ErpErrorResponse> result,
            UseCaseContext context,
            BiFunction<TSuccessResponse, UseCaseContext, TSuccessVerification> verificationFactory) {
        super(result, context, verificationFactory, ErpErrorVerification::new);
    }
}
