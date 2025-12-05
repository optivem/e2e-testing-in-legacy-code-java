package com.optivem.eshop.systemtest.smoketests;

import com.optivem.eshop.systemtest.core.drivers.external.erp.api.ErpApiDriver;
import com.optivem.eshop.systemtest.core.drivers.external.tax.api.TaxApiDriver;
import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.commons.DslContext;
import com.optivem.eshop.systemtest.core.dsl.erp.ErpDsl;
import com.optivem.eshop.systemtest.core.dsl.system.ShopDsl;
import com.optivem.eshop.systemtest.core.dsl.tax.TaxDsl;
import com.optivem.testing.channels.Channel;
import com.optivem.testing.channels.ChannelExtension;
import com.optivem.eshop.systemtest.core.channels.ChannelType;
import com.optivem.eshop.systemtest.core.drivers.DriverFactory;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ChannelExtension.class)
public class ShopSmokeTest {

    private ShopDriver shopDriver;
    private ErpApiDriver erpApiDriver;
    private TaxApiDriver taxApiDriver;

    private ShopDsl shop;
    private ErpDsl erp;
    private TaxDsl tax;

    @BeforeEach
    void setUp() {
        this.shopDriver = DriverFactory.createShopDriver();
        this.erpApiDriver = DriverFactory.createErpApiDriver();
        this.taxApiDriver = DriverFactory.createTaxApiDriver();

        var context = new DslContext();
        shop = new ShopDsl(shopDriver, context);
        erp = new ErpDsl(erpApiDriver, context);
        tax = new TaxDsl(taxApiDriver, context);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpApiDriver);
        Closer.close(taxApiDriver);
    }

    @TestTemplate
    @Channel({ChannelType.UI, ChannelType.API})
    void shouldBeAbleToGoToShop() {
        shop.goToShop();
        shop.confirmShopOpened();
    }
}
