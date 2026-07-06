package com.store.service;

import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.dao.CustomerDAO;
import com.store.model.Customer;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public void addCustomer(String name, String username, String password, String email, long contactNo) {
        if (customerDAO.addCustomer(new Customer(name, username, password, email, contactNo))) {
            Helper.printColored("New Customer Added Successfully.", "green");
        } else {
            Helper.printColored("Error Creating new customer.", "red");
        }
    }

    public void removeCustomer(String username) {
        if (customerDAO.removeCustomer(username)) {
            Helper.printColored("Customer Removed Successfully.", "green");
        } else {
            Helper.printColored("Error Removing Customer.", "red");
        }
    }

    public boolean findCustomerByUsername(String username) {
        if (customerDAO.getCustomerByUsername(username) != null)
            return true;
        else
            return false;
    }

    public Customer getCustomerByUsername(String Username) {
        return customerDAO.getCustomerByUsername(Username);
    }

    public void updateCustomer(Customer a) {
        if (customerDAO.updateCustomer(a)) {
            Helper.printColored("Admin Updated Successfully.", "green");
        } else {
            Helper.printColored("Error Updating Admin", "red");
        }
    }

    public void displayAllCustomer() {
        List<Customer> customers = customerDAO.listCustomer();

        if (customers.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "red");
            return;
        }

        Helper.printList(customers);

    }

    public boolean verifyLogin(String username, String password) {
        return customerDAO.verifyCustomerLogin(username, password);
    }
}
