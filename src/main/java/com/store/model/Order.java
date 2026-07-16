package com.store.model;

import java.sql.Timestamp;

public class Order extends ItemBuy {
    private Timestamp boughtAt;

    public Order(int id, int itemId, String itemName, int customerId, String customerName, int price, int quantity,
            Timestamp boughtAt) {
        super(id, itemId, itemName, customerId, customerName, price, quantity);
        this.boughtAt = boughtAt;
    }

    public Order(int itemId, String itemName, int customerId, String customerName, int price, int quantity,
            Timestamp boughtAt) {
        super(itemId, itemName, customerId, customerName, price, quantity);
        this.boughtAt = boughtAt;
    }

    public Timestamp getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(Timestamp boughtAt) {
        this.boughtAt = boughtAt;
    }
}