package com.store.model;

public class Customer extends User {
    public Customer(int id, String name, String email, String contact, String username, String password, String role) {
        super(id, name, email, contact, username, password, role);
    }

    public Customer(String name, String email, String contact, String username, String password, String role) {
        super(name, email, contact, username, password, role);
    }
}
