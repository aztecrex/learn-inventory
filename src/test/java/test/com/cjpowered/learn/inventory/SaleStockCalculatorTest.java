package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.SaleStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SaleStockCalculatorTest {

    @Test
    public void increasesRequiredDuringSaleDeprecated() {

        // given
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        when(minfo.onSale(item, today)).thenReturn(true);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, minfo, today);

        assertEquals(normalLevel + 20, actual);

    }

    @Test
    public void noIncreaseNormallyDeprecated() {

        // given
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        when(minfo.onSale(item, today)).thenReturn(false);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, minfo, today);

        assertEquals(0, actual);

    }

    @Test
    public void increasesRequiredDuringSale() {

        // given
        MarketingSpec mspec = new MarketingSpec(Season.Spring, true);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, mspec);

        assertEquals(normalLevel + 20, actual);

    }

    @Test
    public void zeroNormally() {

        // given
        MarketingSpec mspec = new MarketingSpec(Season.Spring, false);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(normalLevel, mspec);

        assertEquals(0, actual);

    }

}
