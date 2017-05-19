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
    public int requiredStock(final Item item, final int normalLevel, final InventoryDatabase database,
            final MarketingInfo marketingInfo, final LocalDate when) {
        return normalLevel * (this.season == marketingInfo.season(when) ? 2 : 1);
    }

}
