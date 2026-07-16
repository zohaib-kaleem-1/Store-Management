package com.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.store.db.Database;
import com.store.model.Store;

/**
 * 
 * StoreDAO
 * Handles balance save update during transaction
 * Store name can also be save and updated
 * 
 */
public class StoreDAO {
    /**
     * update the name of store
     * 
     * @param name new name of store
     * @return true if name is updates
     * @throws Exception
     */
    public boolean updateStoreName(String name) throws SQLException {
        String sql = "UPDATE store SET name = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * increase balance of store when customer buy something from store
     * 
     * @param conn    conn from transaction
     * @param balance balance
     * @return true if updated
     * @throws Exception
     */
    public boolean increaseBalance(Connection conn, int balance) throws SQLException {
        String sql = "UPDATE store SET balance = balance + ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, balance);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * decreas balance of store when admin buy something from store
     * 
     * @param conn    conn from transaction
     * @param balance balance
     * @return true if updated
     * @throws Exception
     */
    public boolean decreaseBalance(Connection conn, int balance) throws SQLException {
        String sql = "UPDATE store SET balance = balance - ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, balance);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * get balance of store
     * 
     * @param conn    conn from transaction
     * @param balance balance
     * @return true if updated
     * @throws Exception
     */
    public int getBalance() throws SQLException {
        String sql = "SELECT balance FROM store;";

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("balance");
            } else
                return 0;
        }
    }

    /**
     * Get Store data (name, balance)
     * 
     * @return name and balance of store
     * @throws Exception
     */
    public Store getStoreData() throws SQLException {
        String sql = "SELECT balance, name FROM store;";

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new Store(rs.getString("name"), rs.getInt("balance"));
            } else
                return null;
        }
    }
}