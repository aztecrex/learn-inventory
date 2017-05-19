package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.inventory.StockItem;
import com.cjpowered.learn.marketing.MarketingInfo;

public class ItemTest {

    InventoryDatabase db;
    MarketingInfo minfo;
    LocalDate today;

    @Before
    public void setup() {
        this.db = mock(InventoryDatabase.class);
        this.minfo = mock(MarketingInfo.class);
        this.today = LocalDate.of(2222, 12, 17);
    }

    @Test
    public void itemOverstocked() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(any(), anyInt(), any(), any(), any())).thenReturn(currentLevel - 1);
        final Item item = new StockItem(20, Arrays.asList(calc1), false);
        when(this.db.onHand(item)).thenReturn(currentLevel);

        // when
        final int actual = item.computeOrderQuantity(this.db, this.minfo, this.today);

        // then
        assertEquals(0, actual);

    }

    @Test
    public void itemUnderstocked() {

        // given
        final int currentLevel = 7;
        final int deficiency = 3;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(any(), anyInt(), any(), any(), any())).thenReturn(currentLevel + deficiency);
        final Item item = new StockItem(20, Arrays.asList(calc1), false);
        when(this.db.onHand(item)).thenReturn(currentLevel);

        // when
        final int actual = item.computeOrderQuantity(this.db, this.minfo, this.today);

        // then
        assertEquals(deficiency, actual);

    }

    @Test
    public void itemPicksLargestStockCalculation() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        final int calc1Return = 1000;
        when(calc1.requiredStock(any(), anyInt(), any(), any(), any())).thenReturn(calc1Return);
        final StockCalculator calc2 = mock(StockCalculator.class);
        when(calc2.requiredStock(any(), anyInt(), any(), any(), any())).thenReturn(calc1Return / 2);
        final Item item = new StockItem(20, Arrays.asList(calc1, calc2), false);
        when(this.db.onHand(item)).thenReturn(currentLevel);

        // when
        final int actual = item.computeOrderQuantity(this.db, this.minfo, this.today);

        // then
        assertEquals(calc1Return - currentLevel, actual);

    }

    @Test
    public void itemSufficientStock() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(any(), anyInt(), any(), any(), any())).thenReturn(currentLevel);
        final Item item = new StockItem(20, Arrays.asList(calc1), false);
        when(this.db.onHand(item)).thenReturn(currentLevel);

        // when
        final int actual = item.computeOrderQuantity(this.db, this.minfo, this.today);

        // then
        assertEquals(0, actual);

    }

}
