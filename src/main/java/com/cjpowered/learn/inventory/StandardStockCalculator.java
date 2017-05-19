package com.cjpowered.learn.inventory;

public class StandardStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(int normalLevel, MarketingSpec spec) {

        return normalLevel;

    }

}
