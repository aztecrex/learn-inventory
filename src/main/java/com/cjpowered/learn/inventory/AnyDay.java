package com.cjpowered.learn.inventory;

import java.time.LocalDate;

public class AnyDay implements Schedule {

    @Override
    public boolean canOrder(LocalDate any) {
        return true;
    }

}
