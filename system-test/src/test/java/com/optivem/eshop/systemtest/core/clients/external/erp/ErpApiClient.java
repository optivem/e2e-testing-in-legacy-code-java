package com.optivem.eshop.systemtest.core.clients.external.erp;

import com.optivem.eshop.systemtest.core.clients.commons.TestHttpClient;
import com.optivem.eshop.systemtest.core.clients.external.erp.controllers.HomeController;
import com.optivem.eshop.systemtest.core.clients.external.erp.controllers.ProductController;

import java.net.http.HttpClient;

public class ErpApiClient implements AutoCloseable {

    private final HttpClient client;
    private final TestHttpClient testHttpClient;
    private final HomeController homeController;
    private final ProductController productController;

    public ErpApiClient(String baseUrl) {
        this.client = HttpClient.newHttpClient();
        this.testHttpClient = new TestHttpClient(client, baseUrl);
        this.homeController = new HomeController(testHttpClient);
        this.productController = new ProductController(testHttpClient);
    }

    public HomeController home() {
        return homeController;
    }

    public ProductController products() {
        return productController;
    }

    @Override
    public void close() {
        client.close();
    }
}
