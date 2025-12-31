package com.optivem.eshop.systemtest.core.gherkin.then;

public abstract class BaseThenBuilder {
    private final ThenClause thenClause;

    protected BaseThenBuilder(ThenClause thenClause) {
        this.thenClause = thenClause;
    }

    public ThenClause and() {
        return thenClause;
    }
}
