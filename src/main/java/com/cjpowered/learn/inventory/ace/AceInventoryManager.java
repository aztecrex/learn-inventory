package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.marketing.MarketingInfo;

public final class AceInventoryManager implements InventoryManager {

    private final InventoryDatabase database;
    private final MarketingInfo marketingInfo;
    
    
    public AceInventoryManager(InventoryDatabase database, MarketingInfo marketingInfo) {
        this.database = database;
        this.marketingInfo = marketingInfo;
    }

    @Override
    public List<Order> getOrders(final LocalDate when) {

        final List<Item> items = database.stockItems();
        final List<Order> orders = new ArrayList<>();

        for (Item item : items) {
            final int orderQuantity = item.computeOrderQuantity(database, marketingInfo, when);
            if (orderQuantity > 0) {
                final Order order = new Order(item, orderQuantity);
                orders.add(order);
            }
        }
        return orders;

    }

}
