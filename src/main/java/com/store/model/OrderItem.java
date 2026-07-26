package com.store.model;

public class OrderItem {
    private int orderItemId;
    private int itemId;
    private String itemName;
    private int quantity;
    private int price;
    private int totalPrice;

    public OrderItem(int orderItemId, int itemId, String itemName, int price, int quantity) {
        this(itemId, itemName, price, quantity);
        this.orderItemId = orderItemId;
    }

    public OrderItem(int itemId, String itemName, int price, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getOrderItemId() {
        return orderItemId;
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

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
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
