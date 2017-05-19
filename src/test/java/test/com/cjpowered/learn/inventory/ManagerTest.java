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
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.ace.AceInventoryManager;
/*
 * We need to keep items in stock to prevent back orders. See the README.md
 * for the requirements.
 *
 */
import com.cjpowered.learn.marketing.MarketingInfo;

public class ManagerTest {

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
    public void managerReturnsOrdersForItems() {

        // given
        final int item1Quantity = 12;
        final Item item1 = mock(Item.class);
        when(item1.computeOrderQuantity(this.db, this.minfo, this.today)).thenReturn(item1Quantity);
        final int item2Quantity = 17;
        final Item item2 = mock(Item.class);
        when(item2.computeOrderQuantity(this.db, this.minfo, this.today)).thenReturn(item2Quantity);
        when(this.db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        final Set<Order> expected = new HashSet<>(
                Arrays.asList(new Order(item1, item1Quantity), new Order(item2, item2Quantity)));
        assertEquals(expected, new HashSet<>(actual));

    }

    @Test
    public void managerDoesNotReturnZeroQuantityOrder() {

        // given
        final int item1Quantity = 12;
        final Item item1 = mock(Item.class);
        when(item1.computeOrderQuantity(this.db, this.minfo, this.today)).thenReturn(item1Quantity);
        final Item item2 = mock(Item.class);
        when(item2.computeOrderQuantity(this.db, this.minfo, this.today)).thenReturn(0);
        when(this.db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        final Set<Order> expected = Collections.singleton(new Order(item1, item1Quantity));
        assertEquals(expected, new HashSet<>(actual));

    }

}
