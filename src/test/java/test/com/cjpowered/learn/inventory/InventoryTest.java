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
    
    @Before public void setup() {
        db = mock(InventoryDatabase.class);
        minfo = mock(MarketingInfo.class);
    }
    
    @Test
    public void whenNoStockItemsDoNotOrder() {
        // given
        final LocalDate today = LocalDate.now();
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);

        // then
        assertTrue(actual.isEmpty());

    }
    
    @Test public void orderToLevel() {
        // given
        final LocalDate today = LocalDate.now();
        final int requiredLevel = 15;
        final int currentLevel = 12;
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(db, minfo);
        
        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel - currentLevel, actual.get(0).quantity);
        
    }

    @Test public void overStocked() {
        // given
        final LocalDate today = LocalDate.now();
        int requiredLevel = 15;
        int currentLevel = 25;
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertTrue(actual.isEmpty());
        
        
    }
    
    @Test public void sufficientStock() {
        // given
        final LocalDate today = LocalDate.now();
        int requiredLevel = 15;
        int currentLevel = 15;
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertTrue(actual.isEmpty());
                
    }
    
    @Test public void onSale() {
        final LocalDate today = LocalDate.now();
        int requiredLevel = 15;
        int currentLevel = 11;
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(minfo.onSale(item, today)).thenReturn(true);
        when(db.onHand(item)).thenReturn(currentLevel);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel + 20 - currentLevel, actual.get(0).quantity);
        
    }
    
    @Test public void seasonalInSeason() {
        final LocalDate today = LocalDate.now();
        int requiredLevel = 15;
        int currentLevel = 11;
        final Season season = Season.Fall;
        Item item = new SeasonalItem(requiredLevel, season);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        when(minfo.season(today)).thenReturn(season);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel * 2 - currentLevel, actual.get(0).quantity);
        
    }
    
    @Test public void seasonalNotInSeason() {
        final LocalDate today = LocalDate.now();
        int requiredLevel = 15;
        int currentLevel = 11;
        final Season season = Season.Fall;
        Item item = new SeasonalItem(requiredLevel, Season.Spring);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        when(minfo.season(today)).thenReturn(season);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel - currentLevel, actual.get(0).quantity);
        
    }
    
    @Test public void seasonalAndOnSaleSeasonalBigger() {
        final LocalDate today = LocalDate.now();
        int requiredLevel = 25;
        int currentLevel = 11;
        final Season season = Season.Fall;
        Item item = new SeasonalItem(requiredLevel, season);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        when(minfo.season(today)).thenReturn(season);
        when(minfo.onSale(item, today)).thenReturn(true);
        final InventoryManager im = new AceInventoryManager(db, minfo);

        // when
        final List<Order> actual = im.getOrders(today);
        
        // then
        assertEquals(1, actual.size());
        assertEquals(item, actual.get(0).item);
        assertEquals(requiredLevel * 2 - currentLevel, actual.get(0).quantity);
           
    }
    
    
    
    
}
