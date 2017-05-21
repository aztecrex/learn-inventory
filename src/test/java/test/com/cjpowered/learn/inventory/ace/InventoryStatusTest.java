package test.com.cjpowered.learn.inventory.ace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.ace.InventoryStatus;

public class InventoryStatusTest {

    final InventoryStatus control = new InventoryStatus(1001);

    @Test
    public void reflexiveReferenceEquality() {

        assertTrue(this.control.equals(this.control));
    }

    @Test
    public void valueEquality() {
        // given
        final InventoryStatus copy = duplicate(this.control);

        // then
        assertTrue(this.control.equals(copy));
    }

    @Test
    public void notEqualArbitrary() {

        assertFalse(this.control.equals("not a spec"));
    }

    @Test
    public void notEqualNull() {

        assertFalse(this.control.equals(null));

    }

    @Test
    public void symmetricEquality() {

        // given
        final InventoryStatus copy = duplicate(this.control);

        // then
        assertTrue(copy.equals(this.control));

    }

    @Test
    public void compatibleHash() {

        // given
        final InventoryStatus copy = duplicate(this.control);

        // then
        assertEquals(this.control.hashCode(), copy.hashCode());

    }

    @Test
    public void repeatableHash() {

        assertEquals(this.control.hashCode(), this.control.hashCode());
    }

    @Test
    public void equalComparesOnHand() {

        // given
        final int diffentOnHand = 3;
        require(diffentOnHand != this.control.onHand);
        final InventoryStatus partialCopy = new InventoryStatus(diffentOnHand);

        // then
        assertFalse(this.control.equals(partialCopy));
        assertFalse(partialCopy.equals(this.control));

    }

    private void require(final boolean constraint) {
        if (!constraint)
            throw new RuntimeException("test is not valid");
    }

    private InventoryStatus duplicate(final InventoryStatus from) {
        return new InventoryStatus(from.onHand);
    }

}
