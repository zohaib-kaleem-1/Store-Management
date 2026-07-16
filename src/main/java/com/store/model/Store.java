package com.store.model;

public class Store {
    private String storeName;
    private int balance;

    public Store(String storeName, int balance) {
        this.storeName = storeName;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
