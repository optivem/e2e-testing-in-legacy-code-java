package com.optivem.eshop.systemtest;

import com.optivem.eshop.systemtest.core.ExternalSystemMode;
import com.optivem.eshop.systemtest.core.SystemDsl;

public class SystemDslFactory {
    public static SystemDsl create(ExternalSystemMode externalSystemMode) {
        var configuration = SystemConfigurationLoader.load();
        return new SystemDsl(configuration, externalSystemMode);
    }

    public static SystemDsl create() {
        return create(ExternalSystemMode.STUB);
    }
}
