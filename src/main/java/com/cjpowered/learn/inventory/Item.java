package com.cjpowered.learn.inventory;

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
     * @return
     */
    int computeOrderQuantity(InventoryDatabase database);

}
