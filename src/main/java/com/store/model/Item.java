package com.store.model;

public class Item {
    private int id;
    private String itemName;
    private int itemPrice;
    private int quantity;

    public Item(int id, String itemName, int itemPrice, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public Item(String itemName, int itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
