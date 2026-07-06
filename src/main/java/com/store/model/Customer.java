package com.store.model;

public class Customer {
    private int id;
    private String name;
    private String customerUsername;
    private String password;
    private String email;
    private long contactNo;

    public Customer(String name, String costumerName, String password, String email, long contactNo) {
        this.customerUsername = costumerName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
    }

    public Customer(int id, String name, String costumerName, String password, String email, long contactNo) {
        this.id = id;
        this.customerUsername = costumerName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setCustomerUsername(String costumerName) {
        this.customerUsername = costumerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getContactNo() {
        return contactNo;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
