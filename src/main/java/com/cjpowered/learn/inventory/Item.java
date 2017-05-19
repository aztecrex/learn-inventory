package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

/**
 * An item we can stock. Any instance data will be provided by by the database
 * when you invoke {@link InventoryDatabase#stockItems()}
 *
 */
public interface Item {

    /**
     * Compute the order quantity for a given day.
     *
     * @param database
     * 
     * @param marketingInfo
     *            marketing info service
     * @param when
     *            date for which computation is being made
     * @return the quantity to order. This will never be negative.
     */
    int computeOrderQuantity(InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when);

}
