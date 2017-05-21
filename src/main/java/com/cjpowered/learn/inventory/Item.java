package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.inventory.ace.InventoryStatus;

/**
 * An item we can stock. Any instance data will be provided by by the database
 * when you invoke {@link InventoryDatabase#stockItems()}
 *
 */
public interface Item {

    /**
     * Compute the order quantity for a given day.
     *
     * @param when
     *            date for which computation is being made
     * @param inventoryStatus
     *            current inventory status data
     *
     * @param marketingSpec
     *            current marketing status data
     *
     * @return the quantity to order. This will never be negative.
     */
    int computeOrderQuantity(LocalDate when, InventoryStatus inventoryStatus, MarketingSpec marketingSpec);

}
