package com.store.service;

import com.store.dao.UserDAO;

public class CustomerService extends UserService {
    private UserDAO userDAO;

    public CustomerService() {
        this.userDAO = new UserDAO();
    }

}
