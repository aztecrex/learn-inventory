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
    
    @Test public void valueEquality() {
        // given
        final MarketingSpec copy = duplicate(control);
        
        // then
        assertTrue(control.equals(copy));
    }
    
    @Test public void notEqualArbitrary() {
        
        assertFalse(control.equals("not a spec"));
    }
    
    @Test public void notEqualNull() {
        
        assertFalse(control.equals(null));
        
    }
    
    @Test public void symmetricEquality() {

        // given
        final MarketingSpec copy = duplicate(control);
        
        // then
        assertTrue(copy.equals(control));

        
    }
    
    @Test public void compatibleHash() {
        
        // given
        final MarketingSpec copy = duplicate(control);
        
        // then
        assertEquals(control.hashCode(), copy.hashCode());
        
    }
    
    @Test public void repeatableHash() {
        
        assertEquals(control.hashCode(), control.hashCode());
    }
    
    @Test public void equalComparesSeason() {
        
        // given
        final Season differentSeason = Season.Winter;
        require(!differentSeason.equals(control.season));
        final MarketingSpec partialCopy = new MarketingSpec(differentSeason, control.onSale);
        
        // then
        assertFalse(control.equals(partialCopy));
        assertFalse(partialCopy.equals(control));
        
    }
    
    @Test public void equalComparesOnSale() {
        
        // given
        final boolean differentOnSale = false;
        require(differentOnSale != control.onSale);
        final MarketingSpec partialCopy = new MarketingSpec(control.season, differentOnSale);
        
        // then
        assertFalse(control.equals(partialCopy));
        assertFalse(partialCopy.equals(control));
        
    }
    
    private void require(boolean constraint) {
        if (!constraint)
            throw new RuntimeException("test is not valid");
    }
    
    private MarketingSpec duplicate(MarketingSpec from) {
        return new MarketingSpec(from.season, from.onSale);
    }
    
    
}
