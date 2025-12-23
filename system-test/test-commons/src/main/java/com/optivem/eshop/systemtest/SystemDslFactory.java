package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.SystemDsl;
import com.optivem.testing.dsl.ExternalSystemMode;

public class SystemDslFactory {

    public static SystemDsl create() {
        var environmentMode = getRequiredSystemProperty("environmentMode", "local|acceptance|qa|production");
        var externalSystemMode = getRequiredSystemProperty("externalSystemMode", "stub|real");

        var envMode = EnvironmentMode.valueOf(environmentMode.toUpperCase());
        var extMode = ExternalSystemMode.valueOf(externalSystemMode.toUpperCase());

        return create(envMode, extMode);
    }

    public static SystemDsl create(EnvironmentMode environmentMode, ExternalSystemMode externalSystemMode) {
        var configuration = SystemConfigurationLoader.load(environmentMode, externalSystemMode);
        return new SystemDsl(configuration);
    }

    private static String getRequiredSystemProperty(String propertyName, String allowedValues) {
        var value = System.getProperty(propertyName);
        if (value == null) {
            throw new IllegalStateException(
                String.format("System property '%s' is not defined. Please specify -D%s=<%s>",
                    propertyName, propertyName, allowedValues)
            );
        }
        return value;
    }
}
