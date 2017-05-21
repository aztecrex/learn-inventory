package com.cjpowered.learn.inventory.ace;

import com.cjpowered.learn.inventory.MarketingSpec;

public class StandardStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(final int normalLevel, final MarketingSpec spec) {

        return normalLevel;

    }

}
