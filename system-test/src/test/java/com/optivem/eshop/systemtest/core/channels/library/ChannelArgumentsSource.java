package com.optivem.eshop.systemtest.core.channels.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repeatable annotation to provide inline test arguments that will be combined with channel types.
 * Each @ChannelArgumentsSource annotation represents one row of test arguments that will be
 * executed against all specified channels.
 *
 * Example:
 * <pre>
 * @TestTemplate
 * @Channel({ChannelType.UI, ChannelType.API})
 * @ChannelArgumentsSource("3.5")
 * @ChannelArgumentsSource("lala")
 * void shouldRejectOrderWithNonIntegerQuantity(String nonIntegerQuantity) {
 *     // This test will run 4 times: UI with "3.5", UI with "lala", API with "3.5", API with "lala"
 * }
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ChannelArgumentsSource.Container.class)
public @interface ChannelArgumentsSource {
    /**
     * The test argument values for this row.
     */
    String[] value();

    /**
     * Container annotation for repeated @ChannelArgumentsSource annotations.
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Container {
        ChannelArgumentsSource[] value();
    }
}



