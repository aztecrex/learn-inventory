package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
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
import com.cjpowered.learn.inventory.InventoryStatus;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.inventory.Order;
import com.cjpowered.learn.inventory.ace.AceInventoryManager;
/*
 * We need to keep items in stock to prevent back orders. See the README.md
 * for the requirements.
 *
 */
import com.cjpowered.learn.marketing.MarketingInfo;
import com.cjpowered.learn.marketing.Season;

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
        final Season season = Season.Winter;
        when(this.minfo.season(this.today)).thenReturn(season);

        final MarketingSpec mspec1 = new MarketingSpec(season, true);
        final InventoryStatus istat1 = new InventoryStatus(1000);
        final int quant1 = 15;
        final Item item1 = mock(Item.class);
        when(this.db.onHand(item1)).thenReturn(istat1.onHand);
        when(this.minfo.onSale(item1, this.today)).thenReturn(mspec1.onSale);
        when(item1.computeOrderQuantity(this.today, istat1, mspec1)).thenReturn(quant1);
        final MarketingSpec mspec2 = new MarketingSpec(season, false);
        final InventoryStatus istat2 = new InventoryStatus(25);
        final int quant2 = 17;
        final Item item2 = mock(Item.class);
        when(this.db.onHand(item2)).thenReturn(istat2.onHand);
        when(this.minfo.onSale(item2, this.today)).thenReturn(mspec2.onSale);
        when(item2.computeOrderQuantity(this.today, istat2, mspec2)).thenReturn(quant2);

        when(this.db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        final Set<Order> expected = new HashSet<>(Arrays.asList(new Order(item1, quant1), new Order(item2, quant2)));
        assertEquals(expected, new HashSet<>(actual));

    }

    @Test
    public void managerDoesNotReturnZeroQuantityOrder() {

        // given
        final Item item1 = mock(Item.class);
        when(item1.computeOrderQuantity(any(LocalDate.class), any(), any())).thenReturn(0);

        final Item item2 = mock(Item.class);
        final int quant2 = 1000;
        when(item2.computeOrderQuantity(any(LocalDate.class), any(), any())).thenReturn(quant2);

        when(this.db.stockItems()).thenReturn(Arrays.asList(item1, item2));
        final InventoryManager im = new AceInventoryManager(this.db, this.minfo);

        // when
        final List<Order> actual = im.getOrders(this.today);

        // then
        assertEquals(Collections.singletonList(new Order(item2, quant2)), actual);

    }

}
