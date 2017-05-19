package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.SeasonalItem;
import com.cjpowered.learn.inventory.StockItem;
import com.cjpowered.learn.inventory.ace.AceInventoryManager;
/*
 * We need to keep items in stock to prevent back orders. See the README.md
 * for the requirements.
 *
 */
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

public class InventoryTest {

    InventoryDatabase db;
    MarketingInfo minfo;
    LocalDate today;

    @Test
    public void noOrderIfNotFirstDayOfMonth() {
        // given
        final LocalDate today = LocalDate.of(2112, 9, 2);
        final int requiredLevel = 15;
        final int currentLevel = 9;
        final Item item = new StockItem(requiredLevel, true);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void onSale() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Item item = new StockItem(requiredLevel);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        when(this.minfo.onSale(item, this.today)).thenReturn(true);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel + 20 - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void orderIfFirstDayOfMonth() {
        // given
        final LocalDate today = LocalDate.of(2112, 9, 1);
        final int requiredLevel = 15;
        final int currentLevel = 9;
        final Item item = new StockItem(requiredLevel, true);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void orderToLevel() {
        // given
        final int requiredLevel = 15;
        final int currentLevel = 12;
        final Item item = new StockItem(requiredLevel);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void overStocked() {
        // given
        final int requiredLevel = 15;
        final int currentLevel = 25;
        final Item item = new StockItem(requiredLevel);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void seasonalAndOnSaleSeasonalBigger() {
        final int requiredLevel = 25;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new SeasonalItem(requiredLevel, season);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        when(this.minfo.season(this.today)).thenReturn(season);
        when(this.minfo.onSale(item, this.today)).thenReturn(true);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel * 2 - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void seasonalAndOnSaleSeasonalSmaller() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new SeasonalItem(requiredLevel, season);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        when(this.minfo.season(this.today)).thenReturn(season);
        when(this.minfo.onSale(item, this.today)).thenReturn(true);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel + 20 - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void seasonalInSeason() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new SeasonalItem(requiredLevel, season);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        when(this.minfo.season(this.today)).thenReturn(season);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel * 2 - currentLevel, actual.get(0).quantity);

    }

    @Test
    public void seasonalNotInSeason() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new SeasonalItem(requiredLevel, Season.Spring);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        when(this.minfo.season(this.today)).thenReturn(season);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel - currentLevel, actual.get(0).quantity);

    }

    @Before
    public void setup() {
        this.db = mock(InventoryDatabase.class);
        this.minfo = mock(MarketingInfo.class);
        this.today = LocalDate.of(2222, 12, 17);
    }

    @Test
    public void sufficientStock() {
        // given
        final int requiredLevel = 15;
        final int currentLevel = 15;
        final Item item = new StockItem(requiredLevel);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void whenNoStockItemsDoNotOrder() {
        // given
        final LocalDate today = LocalDate.now();
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }

}
