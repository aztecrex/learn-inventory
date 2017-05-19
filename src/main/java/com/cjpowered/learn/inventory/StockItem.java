package com.cjpowered.learn.inventory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StockItem implements Item {

    final int requiredLevel;

    final boolean firstDayOfMonthOnly;

    final Set<RequiredStockCalculator> requiredStockCalculators;

    public StockItem(final int requiredLevel) {
        this(requiredLevel, false);
    }

    public StockItem(final int normalLevel, final boolean firstDayOfMonthOnly) {
        this(normalLevel, Collections.singletonList(new OnSaleCalculator()), firstDayOfMonthOnly);
    }

    public StockItem(final int normalLevel, final Collection<RequiredStockCalculator> requiredStockCalculators,
            boolean firstDayOfMonthOnly) {
        this.requiredLevel = normalLevel;
        this.requiredStockCalculators = new HashSet<>(requiredStockCalculators);
        this.firstDayOfMonthOnly = firstDayOfMonthOnly;
    }

    @Override
    public int computeOrderQuantity(final InventoryDatabase database, final MarketingInfo marketingInfo,
            final LocalDate when) {
        if (this.firstDayOfMonthOnly && when.getDayOfMonth() != 1)
            return 0;
        
        int requiredLevel = 0;
        for(RequiredStockCalculator calc : requiredStockCalculators) {
            requiredLevel = Math.max(requiredLevel, calc.requiredStock(this, requiredLevel, database, marketingInfo, when));
        }

        final int onHand = database.onHand(this);
        return Math.max(0, requiredLevel - onHand);
    }
}
