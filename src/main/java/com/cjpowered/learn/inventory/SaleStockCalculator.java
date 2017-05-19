package com.cjpowered.learn.inventory;

public class SaleStockCalculator implements StockCalculator {

 
    @Override
    public int requiredStock(int normalLevel, MarketingSpec marketingSpec) {
        return marketingSpec.onSale ? normalLevel + 20 : 0;
    }

}
