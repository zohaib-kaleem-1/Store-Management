package com.store.model;

public class Balance {
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void increaseBalance(int balance) {
        this.balance += balance;
    }

    public void decreaseBalance(int balance) {
        this.balance -= balance;
    }
}
