package com.cjpowered.learn.inventory;

import java.time.LocalDate;

import com.cjpowered.learn.marketing.MarketingInfo;

public interface StockCalculator {

    int requiredStock(Item item, int normalLevel, MarketingInfo marketingInfo, LocalDate when);

}
