package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.SaleStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.marketing.MarketingInfo;

public class SaleStockCalculatorTest {

    @Test
    public void increasesRequiredDuringSale() {

        // given
        final InventoryDatabase db = mock(InventoryDatabase.class);
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        when(minfo.onSale(item, today)).thenReturn(true);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, db, minfo, today);

        assertEquals(normalLevel + 20, actual);

    }

    @Test
    public void noIncreaseNormally() {

        // given
        final InventoryDatabase db = mock(InventoryDatabase.class);
        final MarketingInfo minfo = mock(MarketingInfo.class);
        final LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);
        when(minfo.onSale(item, today)).thenReturn(false);

        final int normalLevel = 175;
        final StockCalculator calc = new SaleStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, db, minfo, today);

        assertEquals(0, actual);

    }

}
