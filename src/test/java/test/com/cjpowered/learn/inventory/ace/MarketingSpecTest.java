package test.com.cjpowered.learn.inventory.ace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cjpowered.learn.inventory.MarketingSpec;
import com.cjpowered.learn.marketing.Season;

public class MarketingSpecTest {

    final MarketingSpec control = new MarketingSpec(Season.Fall, true);

    @Test
    public void reflexiveReferenceEquality() {

        assertTrue(this.control.equals(this.control));
    }

    @Test
    public void valueEquality() {
        // given
        final MarketingSpec copy = duplicate(this.control);

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
        final MarketingSpec copy = duplicate(this.control);

        // then
        assertTrue(copy.equals(this.control));

    }

    @Test
    public void compatibleHash() {

        // given
        final MarketingSpec copy = duplicate(this.control);

        // then
        assertEquals(this.control.hashCode(), copy.hashCode());

    }

    @Test
    public void repeatableHash() {

        assertEquals(this.control.hashCode(), this.control.hashCode());
    }

    @Test
    public void equalComparesSeason() {

        // given
        final Season differentSeason = Season.Winter;
        require(!differentSeason.equals(this.control.season));
        final MarketingSpec partialCopy = new MarketingSpec(differentSeason, this.control.onSale);

        // then
        assertFalse(this.control.equals(partialCopy));
        assertFalse(partialCopy.equals(this.control));

    }

    @Test
    public void equalComparesOnSale() {

        // given
        final boolean differentOnSale = false;
        require(differentOnSale != this.control.onSale);
        final MarketingSpec partialCopy = new MarketingSpec(this.control.season, differentOnSale);

        // then
        assertFalse(this.control.equals(partialCopy));
        assertFalse(partialCopy.equals(this.control));

    }

    private void require(final boolean constraint) {
        if (!constraint)
            throw new RuntimeException("test is not valid");
    }

    private MarketingSpec duplicate(final MarketingSpec from) {
        return new MarketingSpec(from.season, from.onSale);
    }

}
