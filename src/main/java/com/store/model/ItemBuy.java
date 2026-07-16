package com.store.model;

public class ItemBuy {
    private int id;
    private int itemId;
    private String itemName;
    private int customerId;
    private String customerName;
    private int price;
    private int quantity;
    private int totalPrice;

    public ItemBuy(int id, int itemId, String itemName, int customerId, String customerName, int price, int quantity) {
        this(itemId, itemName, customerId, customerName, price, quantity);
        this.id = id;
    }

    public ItemBuy(int itemId, String itemName, int customerId, String customerName, int price, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
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

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}