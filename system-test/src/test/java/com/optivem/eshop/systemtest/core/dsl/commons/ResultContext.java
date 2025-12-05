package com.optivem.eshop.systemtest.core.dsl.commons;

import com.optivem.results.Result;

import java.util.HashMap;
import java.util.Map;

public class ResultContext {
    private final Map<String, String> map;

    public ResultContext() {
        this.map = new HashMap<>();
    }

    public void alias(String alias, String value) {
        if(map.containsKey(alias)) {
            throw new IllegalStateException("Alias already exists: " + alias);
        }

        map.put(alias, value);
    }

    public String alias(String alias) {
        var value = map.get(alias);
        if(value == null) {
            throw new IllegalStateException("Alias not found: " + alias);
        }

        return value;
    }

    public <T> void register(String command, Result<T> result) {
        // TODO: VJ: Implement
    }

    public <T> void register(String command, String key, Result<T> result) {
    }
}