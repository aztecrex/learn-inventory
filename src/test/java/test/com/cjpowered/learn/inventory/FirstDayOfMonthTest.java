package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.FirstDayOfMonth;
import com.cjpowered.learn.inventory.Schedule;

public class FirstDayOfMonthTest {

    @Test
    public void returnsTrueOnFirstDayOfMonth() {

        final LocalDate today = LocalDate.of(2003, 3, 1);
        final Schedule s = new FirstDayOfMonth();

        assertTrue(s.canOrder(today));

    }

    @Test
    public void returnsFalseOnNotFirstDayOfMonth() {

        final LocalDate today = LocalDate.of(2003, 3, 2);
        final Schedule s = new FirstDayOfMonth();

        assertFalse(s.canOrder(today));

    }

}
