package com.cjpowered.learn.inventory;

import java.time.LocalDate;

public class AnyDay implements Schedule {

    @Override
    public boolean canOrder(final LocalDate any) {
        return true;
    }

}
