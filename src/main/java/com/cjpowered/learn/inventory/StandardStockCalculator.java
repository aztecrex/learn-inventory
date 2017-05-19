package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public class StandardStockCalculator implements StockCalculator {

    @Override
    public int requiredStock(final Item item, final int normalLevel, final MarketingInfo marketingInfo,
            final LocalDate when) {
        return normalLevel;
    }

    @Override
    public int requiredStock(StockSpecification spec) {
        
        return spec.normalLevel;
        
    }

}
