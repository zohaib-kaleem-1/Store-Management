package com.store.model;

public class CartItem {
    private int id;

    private String itemName;
    private int itemId;

    private String customerName;
    private int customerId;

    private int quantity;
    private int quantityInStore;

    private int priceOfEachItem;
    private int totalPrice;

    public CartItem(String itemName, int itemId, String customerName, int customerId, int quantity, int quantityInStore,
            int priceOfEachItem) {

        this.itemName = itemName;
        this.itemId = itemId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.quantityInStore = quantityInStore;
        this.priceOfEachItem = priceOfEachItem;

        this.totalPrice = quantity * priceOfEachItem;
    }

    public CartItem(int id, String itemName, int itemId, String customerName, int customerId, int quantity,
            int quantityInStore,
            int priceOfEachItem) {
        this(itemName, itemId, customerName, customerId, quantity, quantityInStore, priceOfEachItem);
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
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

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
