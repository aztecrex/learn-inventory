package test.com.cjpowered.learn.inventory.ace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.ace.SeasonalStockCalculator;
import com.cjpowered.learn.inventory.ace.StockCalculator;
import com.cjpowered.learn.marketing.Season;

public class SeasonalStockCalculatorTest {

    @Test
    public void increaseDuringSeason() {

        // given
        final Season season = Season.Summer;
        final int normalLevel = 175;
        final MarketingSpec spec = new MarketingSpec(season, false);

        final StockCalculator calc = new SeasonalStockCalculator(season);

        // when
        final int actual = calc.requiredStock(normalLevel, spec);

        assertEquals(normalLevel * 2, actual);

    }

    @Test
    public void noIncreaseNormally() {

        // given
        final Season season = Season.Summer;
        final int normalLevel = 175;
        final MarketingSpec spec = new MarketingSpec(season, false);

        final StockCalculator calc = new SeasonalStockCalculator(Season.Winter);

        // when
        final int actual = calc.requiredStock(normalLevel, spec);

        assertEquals(0, actual);

    }

}
