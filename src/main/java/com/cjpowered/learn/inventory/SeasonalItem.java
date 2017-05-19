package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SeasonalItem implements Item {

    final int requiredLevel;

    final Season season;

    public SeasonalItem(final int requiredLevel, final Season season) {
        this.requiredLevel = requiredLevel;
        this.season = season;
    }

    @Override
    public int computeOrderQuantity(final InventoryDatabase database, final MarketingInfo marketingInfo,
            final LocalDate when) {
        final int overrideLevel = Math.max(marketingInfo.onSale(this, when) ? 20 : 0,
                marketingInfo.season(when).equals(this.season) ? this.requiredLevel : 0);
        return Math.max(0, this.requiredLevel + overrideLevel - database.onHand(this));
    }
}
