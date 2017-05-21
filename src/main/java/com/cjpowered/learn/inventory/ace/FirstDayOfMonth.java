package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;

public class FirstDayOfMonth implements Schedule {

    @Override
    public boolean canOrder(final LocalDate any) {
        return any.getDayOfMonth() == 1;
    }

}
