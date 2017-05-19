package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.StandardStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.marketing.Season;

public class StandardStockCalculatorTest {

 
    @Test
    public void returnsNormalLevel() {

        // given
        final int normalLevel = 175;
        final MarketingSpec spec = new MarketingSpec(Season.Summer, false);
        final StockCalculator calc = new StandardStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, spec);

        assertEquals(normalLevel, actual);

    }

}
