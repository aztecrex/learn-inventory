package com.cjpowered.learn.inventory.ace;

import java.time.LocalDate;

public class AnyDay implements Schedule {

    @Override
    public boolean canOrder(final LocalDate any) {
        return true;
    }

}
