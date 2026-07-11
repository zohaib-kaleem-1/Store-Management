package com.store.model;

public class Admin extends User {
    public Admin(int id, String name, String email, String contact, String username, String password, String role) {
        super(id, name, email, contact, username, password, role);
    }

    public Admin(String name, String email, String contact, String username, String password, String role) {
        super(name, email, contact, username, password, role);
    }
}
