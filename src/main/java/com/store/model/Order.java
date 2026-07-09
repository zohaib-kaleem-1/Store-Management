package com.store.model;

public class Order extends Cart {
    private int price;

    public Order(int id, int itemId, int customerId, int quantity, int price) {
        super(id, itemId, customerId, quantity);
        this.price = price;
    }

    public Order(int itemId, int customerId, int quantity, int price) {
        super(itemId, customerId, quantity);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
