package com.cjpowered.learn.inventory;

import java.util.Objects;

import com.cjpowered.learn.marketing.Season;

public final class MarketingSpec {

    public final Season season;

    public final boolean onSale;

    public MarketingSpec(final Season season, final boolean onSale) {
        this.season = season;
        this.onSale = onSale;
    }

    @Override
    public boolean equals(Object obj) {
        if (! ( obj instanceof MarketingSpec))
            return false;
        final MarketingSpec that = (MarketingSpec) obj;
        return Objects.equals(season, that.season) && Objects.equals(onSale, that.onSale);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(season, onSale);
    }
    

}
