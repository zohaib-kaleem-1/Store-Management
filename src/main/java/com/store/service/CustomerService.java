package com.store.service;

import java.sql.SQLException;

import com.store.dao.CustomerDAO;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public Double getBalance(int userid) throws SQLException {
        return customerDAO.getBalance(userid);
    }
}
