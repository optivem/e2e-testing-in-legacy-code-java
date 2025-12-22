package com.optivem.eshop.systemtest.core;

import com.optivem.eshop.systemtest.core.erp.dsl.ErpRealDsl;
import com.optivem.testing.dsl.UseCaseContext;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpDsl;
import com.optivem.eshop.systemtest.core.erp.dsl.ErpStubDsl;
import com.optivem.eshop.systemtest.core.shop.dsl.ShopDsl;
import com.optivem.eshop.systemtest.core.tax.dsl.TaxDsl;
import com.optivem.lang.Closer;

import java.io.Closeable;
import java.util.function.Supplier;

public class SystemDsl implements Closeable {
    private final SystemConfiguration configuration;
    private final ExternalSystemMode externalSystemMode;
    private final UseCaseContext context;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    public SystemDsl(SystemConfiguration configuration, ExternalSystemMode externalSystemMode, UseCaseContext context) {
        this.configuration = configuration;
        this.externalSystemMode = externalSystemMode;
        this.context = context;
    }

    public SystemDsl(SystemConfiguration configuration, ExternalSystemMode externalSystemMode) {
        this(configuration, externalSystemMode, new UseCaseContext());
    }

    @Override
    public void close() {
        Closer.close(shop);
        Closer.close(erp);
        Closer.close(tax);
    }

    public ShopDsl shop() {
        return getOrCreate(shop, () -> shop = new ShopDsl(configuration.getShopUiBaseUrl(), configuration.getShopApiBaseUrl(), context));
    }

    public ErpDsl erp() {
        if(externalSystemMode == ExternalSystemMode.STUB) {
            return getOrCreate(erp, () -> erp = new ErpStubDsl(configuration.getErpStubBaseUrl(), context));
        }
        else if(externalSystemMode == ExternalSystemMode.REAL) {
            return getOrCreate(erp, () -> erp = new ErpRealDsl(configuration.getErpBaseUrl(), context));
        } else {
            throw new IllegalStateException("Unsupported ExternalSystemMode: " + externalSystemMode);
        }
    }

    public TaxDsl tax() {
        return getOrCreate(tax, () -> tax = new TaxDsl(configuration.getTaxBaseUrl(), context));
    }

    private static <T> T getOrCreate(T instance, Supplier<T> supplier) {
        return instance != null ? instance : supplier.get();
    }
}

