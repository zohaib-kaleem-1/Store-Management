package com.store.model;

public class Customer extends User {
    Customer(int id, String name, String email, String contact, String username, String password) {
        super(id, name, email, contact, username, password);
    }

    Customer(String name, String email, String contact, String username, String password) {
        super(name, email, contact, username, password);
    }
}
