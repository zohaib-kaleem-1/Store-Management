package com.store.service;

import java.util.List;

import com.store.dao.UserDAO;
import com.store.model.Customer;
import com.store.model.User;

public class CustomerService {
    private UserDAO userDAO;

    public CustomerService() {
        this.userDAO = new UserDAO();
    }

    public void addCustomer(String name, String username, String password, String email, String contactNo) {
        if (userDAO.addUser(new Customer(name, email, contactNo, username, password), "customer")) {
        } else {
        }
    }

    public void removeCustomer(String username) {
        if (userDAO.removeUser(username, "customer")) {
        } else {
        }
    }

    public boolean findCustomerByUsername(String username) {
        if (userDAO.getUserByUsername(username, "customer") != null)
            return true;
        else
            return false;
    }

    public User getCustomerByUsername(String Username) {
        return userDAO.getUserByUsername(Username, "customer");
    }

    public void updateCustomer(Customer a) {
        if (userDAO.updateUser(a)) {
        } else {
        }
    }

    public void displayAllCustomer() {
        List<User> customers = userDAO.listUser("customer");

        if (customers.isEmpty()) {
            return;
        }
    }

    public boolean verifyLogin(String username, String password) {
        return userDAO.verifyLogin(username, password, "customer");
    }
}
