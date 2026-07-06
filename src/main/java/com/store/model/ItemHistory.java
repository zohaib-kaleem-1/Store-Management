package com.store.model;

public class ItemHistory {
    private int id;
    private int itemID;
    private int customerID;
    private int quantityBought;
    private int price;

    public ItemHistory(int itemID, int customerID, int quantityBought, int price) {
        this.itemID = itemID;
        this.customerID = customerID;
        this.quantityBought = quantityBought;
        this.price = price;
    }

    public ItemHistory(int id, int itemID, int customerID, int quantityBought, int price) {
        this.id = id;
        this.itemID = itemID;
        this.customerID = customerID;
        this.quantityBought = quantityBought;
        this.price = price;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantityBought(int quantityBought) {
        this.quantityBought = quantityBought;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
