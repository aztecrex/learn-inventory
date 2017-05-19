package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StockItem implements Item {

    final int requiredLevel;

    final boolean firstDayOfMonthOnly;

    public StockItem(int requiredLevel) {
        this(requiredLevel, false);
    }

    public StockItem(int requiredLevel, boolean firstDayOfMonthOnly) {
        this.requiredLevel = requiredLevel;
        this.firstDayOfMonthOnly = firstDayOfMonthOnly;
    }

    @Override
    public int computeOrderQuantity(InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when) {
        if (firstDayOfMonthOnly && when.getDayOfMonth() != 1) {
            return 0;
        }
        final int overrideLevel = marketingInfo.onSale(this, when) ? 20 : 0;
        return Math.max(0, requiredLevel + overrideLevel - database.onHand(this));
    }
}
