package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.cjpowered.learn.inventory.AnyDay;
import com.cjpowered.learn.inventory.InventoryStatus;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.Schedule;
import com.cjpowered.learn.inventory.StandardStockCalculator;
import com.cjpowered.learn.inventory.StockCalculator;
import com.cjpowered.learn.inventory.StockItem;
import com.cjpowered.learn.marketing.Season;

public class StockItemTest {

    LocalDate today;

    @Before
    public void setup() {
        this.today = LocalDate.of(2222, 12, 17);
    }

    @Test
    public void itemOverstocked() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(anyInt(), any())).thenReturn(currentLevel - 1);
        final Item item = new StockItem(20, 1, Arrays.asList(calc1), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(0, actual);

    }

    @Test
    public void itemUnderstocked() {

        // given
        final int currentLevel = 7;
        final int deficiency = 3;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(anyInt(), any())).thenReturn(currentLevel + deficiency);
        final Item item = new StockItem(20, 1, Arrays.asList(calc1), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(deficiency, actual);

    }

    @Test
    public void itemPicksLargestStockCalculation() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        final int calc1Return = 1000;
        when(calc1.requiredStock(anyInt(), any())).thenReturn(calc1Return);
        final StockCalculator calc2 = mock(StockCalculator.class);
        when(calc2.requiredStock(anyInt(), any())).thenReturn(calc1Return / 2);
        final Item item = new StockItem(20, 1, Arrays.asList(calc1, calc2), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(calc1Return - currentLevel, actual);

    }

    @Test
    public void itemSufficientStock() {

        // given
        final int currentLevel = 7;

        final StockCalculator calc1 = mock(StockCalculator.class);
        when(calc1.requiredStock(anyInt(), any())).thenReturn(currentLevel);
        final Item item = new StockItem(20, 1, Arrays.asList(calc1), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(0, actual);

    }

    @Test
    public void orderInBulkInexact() {

        // given
        final int currentLevel = 7;
        final int bulkPackageSize = 5;
        final int requiredLevel = currentLevel + bulkPackageSize * 13 + 1;

        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(requiredLevel);
        final Item item = new StockItem(2 /* ignored */, bulkPackageSize, Arrays.asList(calc), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        final int expected = bulkPackageSize * ((requiredLevel - currentLevel) / bulkPackageSize + 1);
        assertEquals(expected, actual);

    }

    @Test
    public void orderInBulkNoneNeeded() {

        // given
        final int currentLevel = 7;
        final int requiredLevel = 7;
        final int bulkPackageSize = 5;

        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(requiredLevel);
        final Item item = new StockItem(2 /* ignored */, bulkPackageSize, Arrays.asList(calc), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(0, actual);

    }

    @Test
    public void orderInBulkOnePackage() {

        // given
        final int currentLevel = 7;
        final int bulkPackageSize = 5;
        final int requiredLevel = currentLevel + bulkPackageSize;

        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(requiredLevel);
        final Item item = new StockItem(2 /* ignored */, bulkPackageSize, Arrays.asList(calc), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(requiredLevel - currentLevel, actual);

    }

    @Test
    public void orderInBulkMultiplePackages() {

        // given
        final int currentLevel = 7;
        final int bulkPackageSize = 5;
        final int requiredLevel = currentLevel + 7 * bulkPackageSize;

        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(requiredLevel);
        final Item item = new StockItem(2 /* ignored */, bulkPackageSize, Arrays.asList(calc), new AnyDay());
        final InventoryStatus istat = new InventoryStatus(currentLevel);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(requiredLevel - currentLevel, actual);

    }

    @Test
    public void doesNotOrderOffSchedule() {

        // given
        final Schedule schedule = mock(Schedule.class);
        when(schedule.canOrder(any())).thenReturn(false);
        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(1000);
        final Item item = new StockItem(2 /* ignored */ , 1, Arrays.asList(new StandardStockCalculator()), schedule);
        final InventoryStatus istat = new InventoryStatus(0);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(0, actual);

    }

    @Test
    public void ordersOnSchedule() {

        // given
        final int requiredStock = 1000;
        final int currentStock = 100;

        final Schedule schedule = mock(Schedule.class);
        when(schedule.canOrder(any())).thenReturn(true);
        final StockCalculator calc = mock(StockCalculator.class);
        when(calc.requiredStock(anyInt(), any())).thenReturn(requiredStock);
        final Item item = new StockItem(2 /* ignored */ , 1, Collections.singletonList(calc), schedule);
        final InventoryStatus istat = new InventoryStatus(currentStock);
        final MarketingSpec mspec = new MarketingSpec(Season.Fall, false);

        // when
        final int actual = item.computeOrderQuantity(this.today, istat, mspec);

        // then
        assertEquals(requiredStock - currentStock, actual);

    }
}
