package com.store.model;

public class CartItem extends Order {
    int quantityInStore;

    public CartItem(int itemId, String itemName, int customerId, String customerName, int price, int quantity,
            int quantityInStore) {
        super(itemId, itemName, customerId, customerName, price, quantity);
        this.quantityInStore = quantityInStore;
    }

    public CartItem(int id, int itemId, String itemName, int customerId, String customerName, int price, int quantity,
            int quantityInStore) {
        super(id, itemId, itemName, customerId, customerName, price, quantity);
        this.quantityInStore = quantityInStore;
    }

    public int getQuantityInStore() {
        return quantityInStore;
    }

    public void setQuantityInStore(int quantityInStore) {
        this.quantityInStore = quantityInStore;
    }
}