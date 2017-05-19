package com.cjpowered.learn.inventory;

import com.cjpowered.learn.marketing.Season;

public final class MarketingSpec {

    public final Season season;
    
    public final boolean onSale;

    public MarketingSpec(Season season, boolean onSale) {
        this.season = season;
        this.onSale = onSale;
    }

}
