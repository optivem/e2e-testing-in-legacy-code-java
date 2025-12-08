package com.optivem.eshop.systemtest.core.dsl.commons.commands;

import com.optivem.eshop.systemtest.core.dsl.commons.context.DslContext;

public abstract class BaseCommand<TDriver, TResult> implements DslCommand<TResult> {
    protected final TDriver driver;
    protected final DslContext context;

    protected BaseCommand(TDriver driver, DslContext context) {
        this.driver = driver;
        this.context = context;
    }

    public abstract TResult execute();
}

