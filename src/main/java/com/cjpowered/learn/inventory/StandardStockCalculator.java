package com.cjpowered.learn.inventory;

public class StandardStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(final int normalLevel, final MarketingSpec spec) {

        return normalLevel;

    }

}
