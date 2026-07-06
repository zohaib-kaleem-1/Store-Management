package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.db.Database;
import com.store.model.Customer;

public class CustomerDAO {
    public boolean addCustomer(Customer customer) {
        Connection conn = null;
        try {
            String sql = "INSERT INTO CUSTOMER (name, username, password, email, contactno) VALUES (?,?, ?,?,?);";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getCustomerUsername());
            stmt.setString(3, customer.getPassword());
            stmt.setString(4, customer.getEmail());
            stmt.setLong(5, customer.getContactNo());

            int rc = stmt.executeUpdate();

            if (rc < 1)
                return false;

            return true;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean removeCustomer(String username) {
        Connection conn = null;
        try {
            String sql = "DELETE FROM customer WHERE username = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rc = stmt.executeUpdate();

            if (rc < 1)
                return false;

            return true;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean updateCustomer(Customer customer) {
        Connection conn = null;
        try {
            String sql = "UPDATE customer SET name = ? ,username = ?, password = ?, email = ? , contactno = ? WHERE customerid = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getCustomerUsername());
            stmt.setString(3, customer.getPassword());
            stmt.setString(4, customer.getEmail());
            stmt.setLong(5, customer.getContactNo());
            stmt.setInt(6, customer.getId());

            int rc = stmt.executeUpdate();

            if (rc < 1)
                return false;

            return true;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public List<Customer> listCustomer() {
        Connection conn = null;
        try {
            List<Customer> customerList = new ArrayList<>();
            String sql = "SELECT * FROM customer;";
            conn = Database.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                customerList.add(new Customer(rs.getInt("customerid"), rs.getString("name"), rs.getString("username"),
                        rs.getString("password"), rs.getString("email"), rs.getLong("contactno")));
            }

            return customerList;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public Customer getCustomerByUsername(String username) {
        Connection conn = null;
        try {
            String sql = "SELECT * FROM customer WHERE username = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return new Customer(rs.getInt("customerid"), rs.getString("name"), rs.getString("username"),
                        rs.getString("password"), rs.getString("email"), rs.getLong("contactno"));
            else
                return null;

        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean verifyCustomerLogin(String username, String password) {
        Connection conn = null;
        try {
            String sql = "SELECT * FROM customer WHERE username = ? and password = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return true;

            return false;

        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }
}