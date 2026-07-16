package com.store.model;

/**
 * CartItem
 * To Store Cart Items and display list
 * extends ItemBuy because it contains quantityInStore variable which is not in
 * store
 */
public class CartItem extends ItemBuy {
    int quantityInStore;

    // Constrcutor for storing to database and passing data to other function
    public CartItem(int itemId, String itemName, int customerId, String customerName, int price, int quantity,
            int quantityInStore) {
        super(itemId, itemName, customerId, customerName, price, quantity);
        this.quantityInStore = quantityInStore;
    }

    // Constructor for reading data from database
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