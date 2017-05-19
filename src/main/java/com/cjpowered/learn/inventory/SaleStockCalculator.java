package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public class SaleStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(Item item, int normalLevel, InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when) {
        return normalLevel + (marketingInfo.onSale(item, when) ? 20 : 0);
    }

}
