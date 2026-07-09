package com.store.model;

public class Cart {
    private int id;
    private int itemId;
    private int customerId;
    private int quantity;

    public Cart(int id, int itemId, int customerId, int quantity) {
        this(itemId, customerId, quantity);
        this.id = id;
    }

    public Cart(int itemId, int customerId, int quantity) {
        this.itemId = itemId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
