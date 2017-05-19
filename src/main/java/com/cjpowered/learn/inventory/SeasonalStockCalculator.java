package com.cjpowered.learn.inventory;

import com.cjpowered.learn.marketing.Season;

public class SeasonalStockCalculator implements StockCalculator {

    private final Season season;

    public SeasonalStockCalculator(final Season season) {
        this.season = season;
    }

    @Override
    public int requiredStock(int normalLevel, MarketingSpec spec) {
        return this.season == spec.season ? normalLevel * 2 : 0;
    }

}
