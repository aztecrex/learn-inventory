package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.marketing.Season;

public class MarketingSpecTest {

    final MarketingSpec control = new MarketingSpec(Season.Fall, true);
    
    @Test public void reflexiveReferenceEquality() {
        
        assertTrue(control.equals(control));
    }
    
    @Test public void reflexiveValueEquality() {
        // given
        final MarketingSpec copy = new MarketingSpec(control.season, control.onSale);
        
        // then
        assertTrue(control.equals(copy));
    }
    
}
