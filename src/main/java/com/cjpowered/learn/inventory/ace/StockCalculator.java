package com.cjpowered.learn.inventory.ace;

import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;

public interface StockCalculator {

    /**
     * Calculate the required stock level for an {@link Item}. If this
     * calculator represents an override, it should return zero when the
     * override conditions are not met.
     *
     * @param item
     *            item to stock, used to make service inquiries
     *
     * @param normalLevel
     *            the normal level to stock for the item
     *
     * @param spec
     *            marketing information
     *
     * @return the required level according to the rule being implemented or
     *         zero if the rule is not in effect for the arguments
     */
    int requiredStock(int normalLevel, MarketingSpec spec);

}
