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
     * Compute the order quantity.
     *
     * @param database
     * @param marketingInfo
     *            TODO
     * @param when
     *            TODO
     * @return
     */
    int computeOrderQuantity(InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when);

}
