package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryDatabase;
import com.cjpowered.learn.inventory.Item;
import com.cjpowered.learn.inventory.RequiredStockCalculator;
import com.cjpowered.learn.inventory.StandardStockCalculator;
import com.cjpowered.learn.marketing.MarketingInfo;

public class StandardStockCalculatorTest {

    @Test
    public void returnsNormalLevel() {

        // given
        InventoryDatabase db = mock(InventoryDatabase.class);
        MarketingInfo minfo = mock(MarketingInfo.class);
        LocalDate today = LocalDate.ofEpochDay(900039);
        final Item item = mock(Item.class);

        final int normalLevel = 175;
        final RequiredStockCalculator calc = new StandardStockCalculator();

        // when
        final int actual = calc.requiredStock(item, normalLevel, db, minfo, today);

        assertEquals(normalLevel, actual);
        
    }

}
