package com.store.model;

public class CartItem {
    private int id;

    private String itemName;
    private int itemId;

    private int quantity;
    private int quantityInStore;

    private int priceOfEachItem;
    private int totalPrice;

    public CartItem(String itemName, int itemId, int quantity, int quantityInStore,
            int priceOfEachItem) {

        this.itemName = itemName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.quantityInStore = quantityInStore;
        this.priceOfEachItem = priceOfEachItem;

        this.totalPrice = quantity * priceOfEachItem;
    }

    public CartItem(int id, String itemName, int itemId, int quantity,
            int quantityInStore,
            int priceOfEachItem) {
        this(itemName, itemId, quantity, quantityInStore, priceOfEachItem);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPriceOfEachItem() {
        return priceOfEachItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuantityInStore() {
        return quantityInStore;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPriceOfEachItem(int priceOfEachItem) {
        this.priceOfEachItem = priceOfEachItem;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantityInStore(int quantityInStore) {
        this.quantityInStore = quantityInStore;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
