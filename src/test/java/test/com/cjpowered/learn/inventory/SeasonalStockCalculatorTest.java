package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.SeasonalStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class SeasonalStockCalculatorTest {

    @Test
    public void increaseDuringSeason() {

        // given
        final InventoryDatabase db = mock(InventoryDatabase.class);
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        final Season season = Season.Summer;
        when(minfo.season(today)).thenReturn(season);

        final int normalLevel = 175;
        final StockCalculator calc = new SeasonalStockCalculator(season);

        // when
        final int actual = calc.requiredStock(item, normalLevel, minfo, today);

        assertEquals(normalLevel * 2, actual);

    }

    @Test
    public void noIncreaseNormally() {

        // given
        final InventoryDatabase db = mock(InventoryDatabase.class);
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        final Season season = Season.Summer;
        when(minfo.season(today)).thenReturn(season);

        final int normalLevel = 175;
        final StockCalculator calc = new SeasonalStockCalculator(Season.Fall);

        // when
        final int actual = calc.requiredStock(item, normalLevel, minfo, today);

        assertEquals(0, actual);

    }

}
