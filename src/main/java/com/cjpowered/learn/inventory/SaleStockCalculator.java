package com.cjpowered.learn.inventory;

public class SaleStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(final int normalLevel, final MarketingSpec marketingSpec) {
        return marketingSpec.onSale ? normalLevel + 20 : 0;
    }

}
