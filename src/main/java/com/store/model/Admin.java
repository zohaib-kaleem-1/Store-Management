package com.store.model;

public class Admin extends User {
    Admin(int id, String name, String email, String contact, String username, String password) {
        super(id, name, email, contact, username, password);
    }

    Admin(String name, String email, String contact, String username, String password) {
        super(name, email, contact, username, password);
    }
}
