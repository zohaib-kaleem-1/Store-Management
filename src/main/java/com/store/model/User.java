package com.store.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String contact;
    private String username;
    private String password;

    User(int id, String name, String email, String contact, String username, String password) {
        this(name, email, contact, username, password);
        this.id = id;
    }

    User(String name, String email, String contact, String username, String password) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.username = username;
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
