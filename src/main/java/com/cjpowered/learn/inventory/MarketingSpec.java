package com.cjpowered.learn.inventory;

import com.cjpowered.learn.marketing.Season;

public final class MarketingSpec {

    public final Season season;

    public final boolean onSale;

    public MarketingSpec(final Season season, final boolean onSale) {
        this.season = season;
        this.onSale = onSale;
    }

}
