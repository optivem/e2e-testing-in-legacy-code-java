package com.optivem.eshop.systemtest.core.dsl.system;

import com.optivem.eshop.systemtest.core.drivers.system.ShopDriver;
import com.optivem.eshop.systemtest.core.dsl.system.commands.CancelOrderCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.GoToShopCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.PlaceOrderCommand;
import com.optivem.eshop.systemtest.core.dsl.system.commands.ViewOrderCommand;

public class ShopDsl {
    private final ShopDriver driver;

    public ShopDsl(ShopDriver driver) {
        this.driver = driver;
    }

    public GoToShopCommand goToShop() {
        return new GoToShopCommand(driver);
    }

    public PlaceOrderCommand placeOrder() {
        return new PlaceOrderCommand(driver);
    }

    public CancelOrderCommand cancelOrder() {
        return new CancelOrderCommand(driver);
    }

    public ViewOrderCommand viewOrder() {
        return new ViewOrderCommand(driver);
    }

}
