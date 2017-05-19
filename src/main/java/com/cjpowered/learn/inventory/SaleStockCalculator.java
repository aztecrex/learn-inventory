package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public class SaleStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(final Item item, final int normalLevel, final InventoryDatabase database,
            final MarketingInfo marketingInfo, final LocalDate when) {
        return marketingInfo.onSale(item, when) ? normalLevel + 20 : 0;
    }

}
