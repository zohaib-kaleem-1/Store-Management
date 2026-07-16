package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.db.Database;
import com.store.model.Item;

/**
 * 
 * ItemDAO
 * 
 * Handles CRUD Operation with database for items table
 * 
 * Methods
 * List Item
 * List available items
 * Add item
 * Remove item
 * Update Item
 */
public class ItemDAO {
    /**
     * List all items from database
     * 
     * @return List of Items
     */

    public List<Item> listItems() throws SQLException {
        List<Item> itemList = new ArrayList<>();
        String sqlQuery = "Select * from Items;";

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
                itemList.add(item);
            }
        }
        return itemList;
    }

    /**
     * List all items from database which are available (quantity greater then 0)
     * 
     * @return List of Items
     */
    public List<Item> listAvailableItems() throws SQLException {
        List<Item> itemList = new ArrayList<>();

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();) {

            String sqlQuery = "Select * from Items WHERE quantity>0;";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("itemid"),
                        rs.getString("itemname"),
                        rs.getInt("price"),
                        rs.getInt("quantity"));
                itemList.add(item);
            }
        }

        return itemList;
    }

    /**
     * Add new item to database
     * 
     * @param item the item to be added
     * @return true if item is inserted into the database
     * @throws Exception
     */
    public boolean addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (itemname, price, quantity) VALUES (?,?,?);";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * Remove items from database
     * 
     * @param id id of item to be removed
     * @return returns true if item is removed
     * @throws Exception if any error is found
     */
    public boolean removeItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE itemid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * Update the information of items
     * 
     * @param item item to be updated
     * @return returns true if updated successfully.
     * @throws Exception throws exception if encounter any issue
     */
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET itemName = ?, price = ? , quantity = ? WHERE itemid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setInt(4, item.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * Increase Quantity when user buys something
     * 
     * @param conn        connection from transaction
     * @param id          id of item to update quantity
     * @param newQuantity quantity to be updated
     * @return true if updated
     * @throws SQLException
     */
    public boolean increaseQuantity(Connection conn, int id, int newQuantity) throws SQLException {
        String sql = "UPDATE items SET quantity = quantity + ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * decrease Quantity when user buys something
     * 
     * @param conn     connection from transaction
     * @param id       id of item to update quantity
     * @param quantity quantity to be updated
     * @return true if updated
     * @throws SQLException
     */
    public boolean decreaseQuantity(Connection conn, int id, int quantity) throws SQLException {
        String sql = "UPDATE items SET quantity = quantity - ? WHERE itemid = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, id);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }
}