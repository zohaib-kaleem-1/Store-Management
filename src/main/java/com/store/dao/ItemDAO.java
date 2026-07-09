package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.db.Database;
import com.store.model.Item;

public class ItemDAO {
    public List<Item> listItems() {
        List<Item> itemList = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {
            String sqlQuery = "Select * from Items;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
                itemList.add(item);
            }
        } catch (Exception e) {

        }

        return itemList;
    }

    public List<Item> listAvailableItems() {
        List<Item> itemList = new ArrayList<>();

        try (Connection conn = Database.getConnection()) {
            String sqlQuery = "Select * from Items WHERE quantity>0;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
                itemList.add(item);
            }
        } catch (Exception e) {

        }
        return itemList;
    }

    public boolean addItem(Item item) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO items (itemname, price, quantity) VALUES (?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());

            return stmt.executeUpdate() < 1 ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeItem(String itemName) {
        try (Connection conn = Database.getConnection()) {
            String sql = "DELETE FROM items WHERE itemname=?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);

            return stmt.executeUpdate() < 1 ? false : true;
        } catch (Exception e) {

            // Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        }
    }

    public boolean updateItem(Item item) {
        try (Connection conn = Database.getConnection()) {
            String sql = "UPDATE items SET itemName = ?, price = ? , quantity = ? WHERE itemid = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getId());

            return stmt.executeUpdate() < 1 ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    public Item getItemByItemName(String itemName) {
        try (Connection conn = Database.getConnection()) {
            String sql = "Select * from Items WHERE itemname = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);

            ResultSet rs = stmt.executeQuery();

            Database.closeConnection(conn);
            if (rs.next())
                return new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
        } catch (Exception e) {

        }

        return null;
    }
}
