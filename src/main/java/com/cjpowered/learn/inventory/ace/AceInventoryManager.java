package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;

public final class AceInventoryManager implements InventoryManager {

    private final InventoryDatabase database;

    public AceInventoryManager(InventoryDatabase database) {
        this.database = database;
    }

    private Order computeOrder(Item item) {
        return new Order(item, 3);
    }

    @Override
    public List<Order> getOrders(final LocalDate today) {

        final Stream<Order> orderStream = database.stockItems().stream().map(item -> {
            return computeOrder(item);
        });

        return orderStream.collect(Collectors.toList());

    }

}
