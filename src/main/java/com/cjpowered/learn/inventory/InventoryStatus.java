package com.cjpowered.learn.inventory;

public final class InventoryStatus {

    public final int onHand;

    public InventoryStatus(final int onHand) {
        this.onHand = onHand;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof InventoryStatus)
            return ((InventoryStatus) obj).onHand == this.onHand;

        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.onHand);
    }

}
