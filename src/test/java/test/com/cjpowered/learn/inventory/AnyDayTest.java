package test.com.cjpowered.learn.inventory;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.cjpowered.learn.inventory.AnyDay;
import com.cjpowered.learn.inventory.Schedule;

public class AnyDayTest {

    @Test public void returnsTrue() {
        
        final LocalDate today = LocalDate.ofEpochDay(3);
        final Schedule s = new AnyDay();
        
        assertTrue(s.canOrder(today));
        
    }
    
}
