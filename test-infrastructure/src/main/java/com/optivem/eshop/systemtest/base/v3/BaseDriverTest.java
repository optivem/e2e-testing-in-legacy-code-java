package com.optivem.eshop.systemtest.base.v3;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.driver.ErpRealDriver;
import com.optivem.eshop.systemtest.core.shop.driver.api.ShopApiDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ShopDriver;
import com.optivem.eshop.systemtest.core.shop.driver.ui.ShopUiDriver;
import com.optivem.eshop.systemtest.core.tax.driver.TaxRealDriver;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseDriverTest extends BaseConfigurableTest {
    private Playwright playwright;
    private Browser browser;
    
    protected ErpRealDriver erpDriver;
    protected TaxRealDriver taxDriver;
    protected ShopDriver shopDriver;
    protected SystemConfiguration configuration;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    void closeBrowser() {
        Closer.close(browser);
        Closer.close(playwright);
    }

    protected void setUpExternalDrivers() {
        configuration = loadConfiguration();
        erpDriver = new ErpRealDriver(configuration.getErpBaseUrl());
        taxDriver = new TaxRealDriver(configuration.getTaxBaseUrl());
    }

    protected void setUpShopApiDriver() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopDriver = new ShopApiDriver(configuration.getShopApiBaseUrl());
    }

    protected void setUpShopUiDriver() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopDriver = new ShopUiDriver(configuration.getShopUiBaseUrl(), browser);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopDriver);
        Closer.close(erpDriver);
        Closer.close(taxDriver);
    }
}
