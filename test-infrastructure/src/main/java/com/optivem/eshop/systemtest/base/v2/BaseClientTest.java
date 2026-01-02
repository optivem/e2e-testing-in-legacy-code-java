package com.optivem.eshop.systemtest.base.v2;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.optivem.eshop.systemtest.configuration.BaseConfigurableTest;
import com.optivem.eshop.systemtest.core.SystemConfiguration;
import com.optivem.eshop.systemtest.core.erp.client.ErpRealClient;
import com.optivem.eshop.systemtest.core.shop.client.api.ShopApiClient;
import com.optivem.eshop.systemtest.core.shop.client.api.dtos.errors.ProblemDetailResponse;
import com.optivem.eshop.systemtest.core.shop.client.ui.ShopUiClient;
import com.optivem.eshop.systemtest.core.tax.client.TaxRealClient;
import com.optivem.http.JsonHttpClient;
import com.optivem.lang.Closer;
import org.junit.jupiter.api.*;

import java.net.http.HttpClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseClientTest extends BaseConfigurableTest {
    private Playwright playwright;
    private Browser browser;
    
    protected ErpRealClient erpClient;
    protected TaxRealClient taxClient;
    protected ShopApiClient shopApiClient;
    protected ShopUiClient shopUiClient;
    protected JsonHttpClient<ProblemDetailResponse> shopHttpClient;
    protected HttpClient rawShopHttpClient;
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

    protected void setUpExternalClients() {
        configuration = loadConfiguration();
        erpClient = new ErpRealClient(configuration.getErpBaseUrl());
        taxClient = new TaxRealClient(configuration.getTaxBaseUrl());
    }

    protected void setUpShopApiClient() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        rawShopHttpClient = HttpClient.newHttpClient();
        shopHttpClient = new JsonHttpClient<>(rawShopHttpClient, configuration.getShopApiBaseUrl(), ProblemDetailResponse.class);
        shopApiClient = new ShopApiClient(shopHttpClient);
    }

    protected void setUpShopUiClient() {
        if (configuration == null) {
            configuration = loadConfiguration();
        }
        shopUiClient = new ShopUiClient(configuration.getShopUiBaseUrl(), browser);
    }

    @AfterEach
    void tearDown() {
        Closer.close(shopUiClient);
        Closer.close(erpClient);
        Closer.close(taxClient);
        Closer.close(rawShopHttpClient);
    }
}

