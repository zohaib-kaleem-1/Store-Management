package com.store.model;

public class Item {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public Item(int id, String itemName, int price, int quantity) {
        this(itemName, price, quantity);
        this.id = id;
    }

    public Item(String itemName, int price, int quantity) {
        this.name = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String itemName) {
        this.name = itemName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
