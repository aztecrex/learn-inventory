package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.StandardStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class StandardStockCalculatorTest {

    @Test
    public void returnsNormalLevelDeprecated() {

        // given
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);

        final int normalLevel = 175;
        final StockCalculator calc = new StandardStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, minfo, today);

        assertEquals(normalLevel, actual);

    }

    @Test
    public void returnsNormalLevel() {

        // given
        final int normalLevel = 175;
        final MarketingSpec spec = new MarketingSpec(Season.Summer);
        final StockCalculator calc = new StandardStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, spec);

        assertEquals(normalLevel, actual);

    }

}
