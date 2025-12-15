package com.optivem.testing.dsl;

import com.optivem.lang.Result;

import java.util.function.BiFunction;

import static com.optivem.testing.assertions.ResultAssert.assertThatResult;

public class UseCaseResult<TResponse, TVerification, E, TFailureVerification extends UseCaseFailureVerification<E>> {
    private final Result<TResponse, E> result;
    private final UseCaseContext context;
    private final BiFunction<TResponse, UseCaseContext, TVerification> verificationFactory;
    private final BiFunction<Result<?, E>, UseCaseContext, TFailureVerification> failureVerificationFactory;

    public UseCaseResult(
            Result<TResponse, E> result,
            UseCaseContext context,
            BiFunction<TResponse, UseCaseContext, TVerification> verificationFactory,
            BiFunction<Result<?, E>, UseCaseContext, TFailureVerification> failureVerificationFactory) {
        this.result = result;
        this.context = context;
        this.verificationFactory = verificationFactory;
        this.failureVerificationFactory = failureVerificationFactory;
    }

    public TVerification shouldSucceed() {
        assertThatResult(result).isSuccess();
        return verificationFactory.apply(result.getValue(), context);
    }

    public TFailureVerification shouldFail() {
        assertThatResult(result).isFailure();
        return failureVerificationFactory.apply(result, context);
    }
}

