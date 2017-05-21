package test.com.cjpowered.learn.inventory.ace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.ace.SaleStockCalculator;
import com.cjpowered.learn.inventory.ace.StockCalculator;
import com.cjpowered.learn.marketing.Season;

public class SaleStockCalculatorTest {

    @Test
    public void increasesRequiredDuringSale() {

        // given
        final MarketingSpec mspec = new MarketingSpec(Season.Spring, true);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, mspec);

        assertEquals(normalLevel + 20, actual);

    }

    @Test
    public void zeroNormally() {

        // given
        final MarketingSpec mspec = new MarketingSpec(Season.Spring, false);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, mspec);

        assertEquals(0, actual);

    }

}
