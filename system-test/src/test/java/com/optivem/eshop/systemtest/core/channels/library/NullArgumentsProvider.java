package com.optivem.eshop.systemtest.core.channels.library;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.stream.Stream;

/**
 * Null implementation of ChannelArgumentsProvider used as the default value
 * when no provider is specified in @ChannelArgumentsSource.
 */
public class NullArgumentsProvider implements ChannelArgumentsProvider {

    @Override
    public Stream<Object[]> provideArguments(ExtensionContext context) {
        return Stream.empty();
    }
}

