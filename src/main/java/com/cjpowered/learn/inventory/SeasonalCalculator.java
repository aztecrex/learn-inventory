package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SeasonalCalculator implements RequiredStockCalculator {

    private final Season season;
    
    public SeasonalCalculator(Season season) {
        this.season = season;
    }
    
    @Override
    public int requiredStock(Item item, int normalLevel, InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when) {
        return normalLevel * (season == marketingInfo.season(when) ? 2 : 1);
    }

}
