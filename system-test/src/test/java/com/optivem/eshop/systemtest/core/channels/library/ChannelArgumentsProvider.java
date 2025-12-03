package com.optivem.eshop.systemtest.core.channels.library;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.stream.Stream;

/**
 * Interface for providing test arguments that will be combined with channel types.
 * Similar to JUnit's ArgumentsProvider but designed to work with @ChannelArgumentsSource.
 *
 * Example:
 * <pre>
 * public class OrderProvider implements ChannelArgumentsProvider {
 *     {@literal @}Override
 *     public Stream{@literal <}Object[]{@literal >} provideArguments(ExtensionContext context) {
 *         return Stream.of(
 *             new Object[]{"SKU123", 5, "US"},
 *             new Object[]{"SKU456", 10, "UK"}
 *         );
 *     }
 * }
 *
 * // Usage:
 * {@literal @}TestTemplate
 * {@literal @}Channel({ChannelType.UI, ChannelType.API})
 * {@literal @}ChannelArgumentsSource(provider = OrderProvider.class)
 * void testOrder(String sku, int quantity, String country) {
 *     // This will run 4 times: UI+row1, UI+row2, API+row1, API+row2
 * }
 * </pre>
 */
public interface ChannelArgumentsProvider {

    /**
     * Provides a stream of argument arrays for parameterized tests.
     * Each array represents one set of arguments for a single test invocation.
     *
     * @param context the current extension context; never null
     * @return a stream of argument arrays; never null
     */
    Stream<Object[]> provideArguments(ExtensionContext context);
}

