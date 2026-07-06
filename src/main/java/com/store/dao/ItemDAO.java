package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.db.Database;
import com.store.model.Item;

public class ItemDAO {
    public List<Item> listItems() {
        Connection conn = null;
        List<Item> itemList = new ArrayList<>();

        try {
            conn = Database.getConnection();

            String sqlQuery = "Select * from Item;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("itemprice"),
                        rs.getInt("itemquantity"));

                itemList.add(item);
            }

            Database.closeConnection(conn);

            return itemList;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public List<Item> listAvailableItems() {
        Connection conn = null;
        List<Item> itemList = new ArrayList<>();

        try {
            conn = Database.getConnection();

            String sqlQuery = "Select * from Item WHERE itemquantity>0;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("itemprice"),
                        rs.getInt("itemquantity"));

                itemList.add(item);
            }

            Database.closeConnection(conn);

            return itemList;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean addItem(Item item) {
        Connection conn = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO item (itemname, itemprice, itemquantity) VALUES (?,?,?);";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getItemPrice());
            stmt.setInt(3, item.getQuantity());

            stmt.executeUpdate();

            return true;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");

            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean removeItem(String itemName) {
        Connection conn = null;
        try {
            conn = Database.getConnection();

            String sql = "DELETE FROM item WHERE itemname=?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);

            int rc = stmt.executeUpdate();
            if (rc < 1)
                return false;

            Database.closeConnection(conn);
            return true;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean updateItem(Item item) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "UPDATE item SET itemName = ?, itemPrice = ? , itemQuantity = ? WHERE itemid = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getItemPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getId());

            int rc = stmt.executeUpdate();
            if (rc < 1)
                return false;

            Database.closeConnection(conn);
            return true;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public Item getItemByItemName(String itemName) {
        Connection conn = null;
        try {
            conn = Database.getConnection();

            String sql = "Select * from Item WHERE itemname = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);

            ResultSet rs = stmt.executeQuery();

            Database.closeConnection(conn);
            if (rs.next())
                return new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("itemprice"),
                        rs.getInt("itemquantity"));

            return null;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(conn);
        }
    }
}
