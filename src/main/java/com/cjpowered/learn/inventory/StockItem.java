package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StockItem implements Item {

    final int requiredLevel;

    public StockItem(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public int computeOrderQuantity(InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when) {
        final int overrideLevel = marketingInfo.onSale(this, when) ? 20 : 0;
        return Math.max(0, requiredLevel + overrideLevel - database.onHand(this));
    }
}
