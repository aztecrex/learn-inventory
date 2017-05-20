package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.InventoryStatus;

public class InventoryStatusTest {

    final InventoryStatus control = new InventoryStatus(1001);
    
    @Test public void reflexiveReferenceEquality() {
        
        assertTrue(control.equals(control));
    }
    
    @Test public void valueEquality() {
        // given
        final InventoryStatus copy = duplicate(control);
        
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
        final InventoryStatus copy = duplicate(control);
        
        // then
        assertTrue(copy.equals(control));

        
    }
    
    @Test public void compatibleHash() {
        
        // given
        final InventoryStatus copy = duplicate(control);
        
        // then
        assertEquals(control.hashCode(), copy.hashCode());
        
    }
    
    @Test public void repeatableHash() {
        
        assertEquals(control.hashCode(), control.hashCode());
    }
    
    @Test public void equalComparesOnHand() {
        
        // given
        final int diffentOnHand = 3;
        require(diffentOnHand != control.onHand);
        final InventoryStatus partialCopy = new InventoryStatus(diffentOnHand);
        
        // then
        assertFalse(control.equals(partialCopy));
        assertFalse(partialCopy.equals(control));
        
    }
    
    private void require(boolean constraint) {
        if (!constraint)
            throw new RuntimeException("test is not valid");
    }
    
    private InventoryStatus duplicate(InventoryStatus from) {
        return new InventoryStatus(from.onHand);
    }
    
    
}
