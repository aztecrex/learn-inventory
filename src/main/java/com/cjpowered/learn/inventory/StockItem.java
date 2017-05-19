package com.cjpowered.learn.inventory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StockItem implements Item {

    final int normalLevel;

    final Set<StockCalculator> requiredStockCalculators;

    final int packageSize;

    final Schedule schedule;

    /**
     * Create a stock item
     *
     * @param normalLevel
     *            normal stock level. actual required stock level can be based
     *            on this
     *
     * @param packageSize
     *            number of units in each package when ordered
     *
     * @param stockCalculators
     *            required stock level calculators. There is no default
     *            calculator so at least a {@link StandardStockCalculator}
     *            should be provided
     *
     * @param schedule
     *            schedule on which orders can be placed. If no restrictions,
     *            use {@link AnyDay}.
     */
    public StockItem(final int normalLevel, final int packageSize, final Collection<StockCalculator> stockCalculators,
            final Schedule schedule) {
        this.normalLevel = normalLevel;
        this.packageSize = packageSize;
        this.requiredStockCalculators = new HashSet<>(stockCalculators);
        this.schedule = schedule;
    }

    @Override
    public int computeOrderQuantity(final InventoryDatabase database, final MarketingInfo marketingInfo,
            final LocalDate when) {

        if (!this.schedule.canOrder(when))
            return 0;

        int requiredLevel = 0;
        for (final StockCalculator calc : this.requiredStockCalculators) {
            requiredLevel = Math.max(requiredLevel, calc.requiredStock(this, this.normalLevel, marketingInfo, when));
        }

        final int onHand = database.onHand(this);
        final int needed = Math.max(0, requiredLevel - onHand);

        final int packages = needed / this.packageSize + (needed % this.packageSize == 0 ? 0 : 1);
        return packages * this.packageSize;

    }
}
