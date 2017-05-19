package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.OnSaleCalculator;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.SeasonalCalculator;
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

    @Before
    public void setup() {
        this.db = mock(InventoryDatabase.class);
        this.minfo = mock(MarketingInfo.class);
        this.today = LocalDate.of(2222, 12, 17);
    }

    @Test
    public void managerHandlesZeroItems() {
        // given
        final LocalDate today = LocalDate.now();
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void orderToLevel() {
        // given
        final int requiredLevel = 15;
        final int currentLevel = 12;
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()));
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
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()));
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void sufficientStock() {
        // given
        final int requiredLevel = 15;
        final int currentLevel = 15;
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()));
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void onSale() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()));
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
    public void seasonalInSeason() {
        final int requiredLevel = 15;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new StockItem(requiredLevel,
                Arrays.asList(new OnSaleCalculator(), new SeasonalCalculator(season)),
                false);
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
        final Item item = new StockItem(requiredLevel,
                Arrays.asList(new OnSaleCalculator(), new SeasonalCalculator(Season.Spring)),
                false);
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

    @Test
    public void seasonalAndOnSaleSeasonalBigger() {
        final int requiredLevel = 25;
        final int currentLevel = 11;
        final Season season = Season.Fall;
        final Item item = new StockItem(requiredLevel,
                Arrays.asList(new OnSaleCalculator(), new SeasonalCalculator(season)),
                false);
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
        final Item item = new StockItem(requiredLevel,
                Arrays.asList(new OnSaleCalculator(), new SeasonalCalculator(season)),
                false);
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
    public void orderIfFirstDayOfMonth() {
        // given
        final LocalDate today = LocalDate.of(2112, 9, 1);
        final int requiredLevel = 15;
        final int currentLevel = 9;
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()), true);
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
    public void noOrderIfNotFirstDayOfMonth() {
        // given
        final LocalDate today = LocalDate.of(2112, 9, 2);
        final int requiredLevel = 15;
        final int currentLevel = 9;
        final Item item = new StockItem(requiredLevel, Arrays.asList(new OnSaleCalculator()), true);
        when(this.db.stockItems()).thenReturn(Collections.singletonList(item));
        when(this.db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }

    @Test
    public void managerReturnsOrdersForItems() {

        // given
        final int item1Quantity = 12;
        final Item item1 = mock(Item.class);
        when(item1.computeOrderQuantity(db, minfo, today)).thenReturn(item1Quantity);
        final int item2Quantity = 17;
        final Item item2 = mock(Item.class);
        when(item2.computeOrderQuantity(db, minfo, today)).thenReturn(item2Quantity);
        when(db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        Set<Order> expected = new HashSet<>(
                Arrays.asList(new Order(item1, item1Quantity), new Order(item2, item2Quantity)));
        assertEquals(expected, new HashSet<>(actual));
        

    }

    @Test
    public void managerDoesNotReturnZeroQuantityOrder() {

        // given
        final int item1Quantity = 12;
        final Item item1 = mock(Item.class);
        when(item1.computeOrderQuantity(db, minfo, today)).thenReturn(item1Quantity);
        final Item item2 = mock(Item.class);
        when(item2.computeOrderQuantity(db, minfo, today)).thenReturn(0);
        when(db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        Set<Order> expected = Collections.singleton(new Order(item1, item1Quantity)); 
        assertEquals(expected, new HashSet<>(actual));
        

    }

}
