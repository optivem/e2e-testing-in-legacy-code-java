package com.optivem.eshop.systemtest.e2etests.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

/**
 * Provides test arguments for empty quantity validation tests.
 */
public class EmptyQuantityArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
            Arguments.of("", "Quantity must not be empty"),      // Empty string
            Arguments.of("   ", "Quantity must not be empty")    // Whitespace string
        );
    }
}

