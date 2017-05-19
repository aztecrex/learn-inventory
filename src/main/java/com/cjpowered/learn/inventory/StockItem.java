package com.cjpowered.learn.inventory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StockItem implements Item {

    final int normalLevel;

    final boolean firstDayOfMonthOnly;

    final Set<StockCalculator> requiredStockCalculators;

    public StockItem(final int normalLevel, final Collection<StockCalculator> requiredStockCalculators) {
        this(normalLevel, requiredStockCalculators, false);
    }

    public StockItem(final int normalLevel, final Collection<StockCalculator> requiredStockCalculators,
            final boolean firstDayOfMonthOnly) {
        this.normalLevel = normalLevel;
        this.requiredStockCalculators = new HashSet<>(requiredStockCalculators);
        this.firstDayOfMonthOnly = firstDayOfMonthOnly;
    }

    @Override
    public int computeOrderQuantity(final InventoryDatabase database, final MarketingInfo marketingInfo,
            final LocalDate when) {
        if (this.firstDayOfMonthOnly && when.getDayOfMonth() != 1)
            return 0;

        int requiredLevel = 0;
        for (final StockCalculator calc : this.requiredStockCalculators) {
            requiredLevel = Math.max(requiredLevel,
                    calc.requiredStock(this, this.normalLevel, database, marketingInfo, when));
        }

        final int onHand = database.onHand(this);
        return Math.max(0, requiredLevel - onHand);
    }
}
