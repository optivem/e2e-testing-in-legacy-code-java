package com.optivem.testing.dsl;

import com.optivem.lang.Result;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class UseCaseFailureVerification<E> {
    private final Result<?, E> result;
    private final UseCaseContext context;

    public UseCaseFailureVerification(Result<?, E> result, UseCaseContext context) {
        this.result = result;
        this.context = context;
    }

    public E getError() {
        return result.getError();
    }

    public UseCaseContext getContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    public <T extends UseCaseFailureVerification<E>> T assertError(E expectedError) {
        E actualError = result.getError();
        assertThat(actualError)
                .withFailMessage("Expected error: '%s', but got: '%s'", expectedError, actualError)
                .isEqualTo(expectedError);
        return (T) this;
    }
}

