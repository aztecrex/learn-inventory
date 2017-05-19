package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SeasonalItem implements Item {

    final int requiredLevel;
    
    final Season season;

    public SeasonalItem(int requiredLevel, Season season) {
        this.requiredLevel = requiredLevel;
        this.season = season;
    }

    @Override
    public int computeOrderQuantity(InventoryDatabase database, MarketingInfo marketingInfo, LocalDate when) {
        final int overrideLevel = 
                (marketingInfo.onSale(this, when) ? 20 : 0)
                + (marketingInfo.season(when).equals(season) ? requiredLevel : 0);
        return Math.max(0, requiredLevel + overrideLevel - database.onHand(this));
    }
}
