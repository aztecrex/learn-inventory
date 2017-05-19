package com.cjpowered.learn.inventory;

import java.time.LocalDate;

public class FirstDayOfMonth implements Schedule {

    @Override
    public boolean canOrder(LocalDate any) {
        return any.getDayOfMonth() == 1;
    }

}
