package com.cjpowered.learn.inventory;

public final class InventoryStatus {

    public final int onHand;

    public InventoryStatus(int onHand) {
        this.onHand = onHand;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj)
            return true;
        else if (obj instanceof InventoryStatus)
            return ((InventoryStatus) obj).onHand == onHand;
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(onHand);
    }
    
}
