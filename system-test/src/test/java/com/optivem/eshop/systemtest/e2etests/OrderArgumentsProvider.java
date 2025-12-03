package com.optivem.eshop.systemtest.e2etests;

import com.optivem.eshop.systemtest.core.channels.library.ChannelArgumentsProvider;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.stream.Stream;

/**
 * Example provider that demonstrates how to use ChannelArgumentsProvider
 * to supply complex test data including objects.
 */
public class OrderArgumentsProvider implements ChannelArgumentsProvider {

    @Override
    public Stream<Object[]> provideArguments(ExtensionContext context) {
        return Stream.of(
            new Object[]{"SKU-PREMIUM-001", 5, "US"},
            new Object[]{"SKU-BASIC-002", 10, "UK"}
        );
    }
}

