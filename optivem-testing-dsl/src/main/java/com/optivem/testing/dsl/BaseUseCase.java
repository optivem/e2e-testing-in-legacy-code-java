package com.optivem.testing.dsl;

public abstract class BaseUseCase<TDriver, TResponse, TVerification, E, TFailureVerification extends UseCaseFailureVerification<E>> implements UseCase<UseCaseResult<TResponse, TVerification, E, TFailureVerification>> {
    protected final TDriver driver;
    protected final UseCaseContext context;

    protected BaseUseCase(TDriver driver, UseCaseContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract UseCaseResult<TResponse, TVerification, E, TFailureVerification> execute();
}

