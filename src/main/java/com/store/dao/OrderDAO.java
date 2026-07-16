package com.store.dao;

import com.store.db.Database;
import com.store.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * OrderDAO
 * 
 * Contains CRUD Operations for order table
 */
public class OrderDAO {
    /**
     * Add a new order when customer buys something
     * 
     * @param conn       Connection to database from transaction
     * @param itemId     id of itembought
     * @param customerId customerid bought
     * @param price      price of item bought
     * @param quantity   quantity of items bought
     * @return true if row is added
     * @throws Exception throws exception if error is caught
     */
    public boolean addOrder(Connection conn, int itemId, int customerId, int price, int quantity) throws SQLException {
        String sql = "INSERT INTO orders (itemid, userid, quantity, price) VALUES (?,?,?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);
            stmt.setInt(3, price);
            stmt.setInt(4, quantity);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * List all orders (Admin use)
     * 
     * @return List of order
     */
    public List<Order> listOrder() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.orderid,i.itemid,o.bought_at, i.itemname, u.userid, u.name, o.price, o.quantity FROM orders o JOIN items i ON i.itemid = o.itemid JOIN users u ON u.userid = o.userid ;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("orderid"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("userid"),
                        rs.getString("name"), rs.getInt("price"), rs.getInt("quantity"), rs.getTimestamp("bought_at")));
            }
        }
        return list;
    }

    /**
     * List order by customer id (customer use)
     * 
     * @param customerId id of customer of whom order is fetched
     * @return list of order
     */
    public List<Order> listOrderByCustomerId(int customerId) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.orderid,o.bought_at i.itemname, i.itemid, o.price, o.quantity FROM orders o JOIN items i ON i.itemid = o.itemid JOIN users u ON u.userid = o.userid WHERE o.userid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("orderid"), rs.getInt("itemid"), rs.getString("itemname"), customerId, "",
                        rs.getInt("price"), rs.getInt("quantity"), rs.getTimestamp("bought_at")));
            }
        }
        return list;
    }
}
