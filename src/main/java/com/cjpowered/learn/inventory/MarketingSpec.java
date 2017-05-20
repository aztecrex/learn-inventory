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
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof MarketingSpec)
            return ((MarketingSpec) obj).onSale == this.onSale && ((MarketingSpec) obj).season.equals(this.season);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.season, this.onSale);
    }

}
