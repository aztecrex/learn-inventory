package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.InventoryManager;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.StockItem;
import com.cjpowered.learn.inventory.ace.AceInventoryManager;
/*
 * We need to keep items in stock to prevent back orders. See the README.md
 * for the requirements.
 *
 */
import com.cjpowered.learn.marketing.MarketingInfo;

public class InventoryTest {

    @Test
    public void whenNoStockItemsDoNotOrder() {
        // given
        final LocalDate today = LocalDate.now();
        final InventoryDatabase db = mock(InventoryDatabase.class);
        MarketingInfo minfo = mock(MarketingInfo.class);
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
        InventoryDatabase db = mock(InventoryDatabase.class);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        MarketingInfo minfo = mock(MarketingInfo.class);
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
        InventoryDatabase db = mock(InventoryDatabase.class);
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        MarketingInfo minfo = mock(MarketingInfo.class);
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
        InventoryDatabase db = mock(InventoryDatabase.class);
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        when(db.onHand(item)).thenReturn(currentLevel);
        MarketingInfo minfo = mock(MarketingInfo.class);
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
        InventoryDatabase db = mock(InventoryDatabase.class);
        Item item = new StockItem(requiredLevel);
        when(db.stockItems())
        .thenReturn(Collections.singletonList(item));
        MarketingInfo minfo = mock(MarketingInfo.class);
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
    
}
