package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseCommand<TDriver, TResponse, TVerification> implements DslCommand<CommandResult<TResponse, TVerification>> {
    protected final TDriver driver;
    protected final DslContext context;

    protected BaseCommand(TDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract CommandResult<TResponse, TVerification> execute();
}

