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

    public boolean addItem(Connection conn, Item item) throws Exception {
        String sql = "INSERT INTO items (itemname, price, quantity) VALUES (?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());

            return stmt.executeUpdate() < 1 ? false : true;
        }
    }

    public boolean removeItem(Connection conn, String itemName) throws Exception {
        String sql = "DELETE FROM items WHERE itemname=?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, itemName);

            return stmt.executeUpdate() < 1 ? false : true;
        }
    }

    public boolean updateItem(Connection conn, Item item) throws Exception {
        String sql = "UPDATE items SET itemName = ?, price = ? , quantity = ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getId());

            return stmt.executeUpdate() < 1 ? false : true;
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

    public boolean updatePrice(Connection conn, int itemid, int updatePrice) throws Exception {
        String sql = "UPDATE items SET price = price + ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, updatePrice);
            stmt.setInt(2, itemid);

            return stmt.executeUpdate() < 1 ? false : true;
        }
    }

    public boolean updateName(Connection conn, int itemid, String newName) throws Exception {
        String sql = "UPDATE items SET itemname =  ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setInt(2, itemid);

            return stmt.executeUpdate() < 1 ? false : true;
        }
    }

    public boolean updateQuantity(Connection conn, int itemid, int updatedQuantity) throws Exception {
        String sql = "UPDATE items SET quantity = quantity + ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, updatedQuantity);
            stmt.setInt(2, itemid);

            return stmt.executeUpdate() < 1 ? false : true;
        }
    }
}