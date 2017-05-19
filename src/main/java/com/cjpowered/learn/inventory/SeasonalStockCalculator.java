package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SeasonalStockCalculator implements StockCalculator {

    private final Season season;

    public SeasonalStockCalculator(final Season season) {
        this.season = season;
    }

    @Override
    public int requiredStock(final Item item, final int normalLevel, final MarketingInfo marketingInfo,
            final LocalDate when) {
        return this.season == marketingInfo.season(when) ? normalLevel * 2 : 0;
    }

    @Override
    public int requiredStock(StockSpecification spec) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("NYI");
    }

}
