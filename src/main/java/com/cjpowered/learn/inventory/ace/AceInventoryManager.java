package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.InventoryStatus;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public final class AceInventoryManager implements InventoryManager {

    private final InventoryDatabase database;
    private final MarketingInfo marketingInfo;

    public AceInventoryManager(final InventoryDatabase database, final MarketingInfo marketingInfo) {
        this.database = database;
        this.marketingInfo = marketingInfo;
    }

    @Override
    public List<Order> getOrders(final LocalDate when) {

        final List<Item> items = this.database.stockItems();
        final List<Order> orders = new ArrayList<>();

        final Season season = this.marketingInfo.season(when);

        for (final Item item : items) {
            final int onHand = this.database.onHand(item);
            final boolean onSale = this.marketingInfo.onSale(item, when);
            final InventoryStatus inventoryStatus = new InventoryStatus(onHand);
            final MarketingSpec marketingStatus = new MarketingSpec(season, onSale);
            final int orderQuantity = item.computeOrderQuantity(when, inventoryStatus, marketingStatus);
            if (orderQuantity > 0) {
                final Order order = new Order(item, orderQuantity);
                orders.add(order);
            }
        }
        return orders;

    }

}
