package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

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
     * @param marketingInfo
     *            marketing information service
     *
     * @param when
     *            the date for which the required level is being calculated
     *
     * @return the required level according to the rule being implemented or
     *         zero if the rule is not in effect for the arguments
     */
    int requiredStock(Item item, int normalLevel, MarketingInfo marketingInfo, LocalDate when);

}
