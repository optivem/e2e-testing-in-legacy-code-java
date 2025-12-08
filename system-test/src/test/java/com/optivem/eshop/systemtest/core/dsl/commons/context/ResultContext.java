package com.optivem.eshop.systemtest.core.dsl.commons.context;

import com.optivem.results.Result;

import java.util.HashMap;
import java.util.Map;

public class ResultContext {
    private final Map<String, String> aliasMap;

    public ResultContext() {
        this.aliasMap = new HashMap<>();
    }

    public void setAliasValue(String alias, String value) {
        if(aliasMap.containsKey(alias)) {
            throw new IllegalStateException("Alias already exists: " + alias);
        }

        aliasMap.put(alias, value);
    }

    public String getAliasValue(String alias) {
        var value = aliasMap.get(alias);
        if(value == null) {
            throw new IllegalStateException("Alias not found: " + alias);
        }

        return value;
    }
}