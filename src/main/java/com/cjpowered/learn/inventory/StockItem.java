package com.cjpowered.learn.inventory;

public class StockItem implements Item {

    final int requiredLevel;
    
    public StockItem(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public int computeOrderQuantity(InventoryDatabase database) {
        return Math.max(0, requiredLevel - database.onHand(this));
    }
    
    

}
