package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.db.Database;
import com.store.model.ItemHistory;

public class ItemHistoryDAO {
    public List<ItemHistory> listItem() {
        Connection conn = null;
        List<ItemHistory> history = new ArrayList<>();
        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM itemhistory;";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ItemHistory item = new ItemHistory(rs.getInt("id"), rs.getInt("itemId"), rs.getInt("customerID"),
                        rs.getInt("quantity"), rs.getInt("price"));

                history.add(item);
            }
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
        return history;
    }

    public List<ItemHistory> GetItemHistoryByItemId(int id) {
        Connection conn = null;
        List<ItemHistory> history = new ArrayList<>();
        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM itemhistory WHERE itemid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemHistory item = new ItemHistory(rs.getInt("id"), rs.getInt("itemId"), rs.getInt("customerID"),
                        rs.getInt("quantity"), rs.getInt("price"));
                history.add(item);
            }
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
        return history;
    }

    public List<ItemHistory> GetItemHistoryByCustomerId(int id) {
        Connection conn = null;
        List<ItemHistory> history = new ArrayList<>();
        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM itemhistory WHERE customerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemHistory item = new ItemHistory(rs.getInt("id"), rs.getInt("itemId"), rs.getInt("customerID"),
                        rs.getInt("quantity"), rs.getInt("price"));
                history.add(item);
            }
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
        return history;
    }

    public boolean addItemHistory(ItemHistory history) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO itemhistory (itemid, customerid, quantity, price) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, history.getItemID());
            stmt.setInt(2, history.getCustomerID());
            stmt.setInt(3, history.getQuantityBought());
            stmt.setInt(4, history.getPrice());
            int rc = stmt.executeUpdate();
            if (rc < 1)
                return false;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
        return true;
    }

    public boolean updateItemHistory(ItemHistory history) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "UPDATE itemhistory SET itemid = ?, customerid = ? , quantity = ? , price = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, history.getItemID());
            stmt.setInt(2, history.getCustomerID());
            stmt.setInt(3, history.getQuantityBought());
            stmt.setInt(4, history.getPrice());
            stmt.setInt(5, history.getId());
            int rc = stmt.executeUpdate();
            if (rc < 1)
                return false;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
        return true;
    }

    public boolean removeItemHistory(int id) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "DELETE FROM itemhistory WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rc = stmt.executeUpdate();

            if (rc < 1)
                return false;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
        return true;
    }
}
