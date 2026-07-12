package com.store.dao;

import com.store.db.Database;
import com.store.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public boolean addOrder(Connection conn, int itemId, int customerId, int price, int quantity) throws Exception {
        String sql = "INSERT INTO orders (itemid, customerid, quantity, price) VALUES (?,?,?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);
            stmt.setInt(3, price);
            stmt.setInt(4, quantity);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public boolean updateOrder(Connection conn, Order o) throws Exception {
        String sql = "UPDATE orders SET itemid = ?, customerid = ?, quantity = ?,price = ? WHERE id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getItemId());
            stmt.setInt(2, o.getCustomerId());
            stmt.setInt(3, o.getQuantity());
            stmt.setInt(3, o.getPrice());
            stmt.setInt(4, o.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public boolean removeOrder(Connection conn, int id) throws Exception {
        String sql = "DELETE from orders WHERE id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public List<Order> listFromOrder() {
        List<Order> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT o.id,i.itemid, i.itemname, u.userid, u.name, o.price, o.quantity FROM orders o JOIN items i ON i.itemid = o.itemid JOIN users u ON u.userid = o.customerid ;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), rs.getInt("itemid"), rs.getString("itemname"), rs.getInt("userid"),
                        rs.getString("name"), rs.getInt("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Order> listFromOrderByItemId(int itemid) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, i.itemname, u.userid, u.name, o.price, o.quantity, (o.price*o.quantity) AS totalPrice FROM orders o JOIN items i ON i.itemid = o.itemid JOIN users u ON u.userid = o.customerid WHERE o.itemid = ?;";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), itemid, rs.getString("itemname"), rs.getInt("userid"),
                        rs.getString("name"), rs.getInt("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Order> listFromOrderByCustomerId(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, i.itemname, i.itemid, u.name, o.price, o.quantity, (o.price*o.quantity) AS totalPrice FROM orders o JOIN items i ON i.itemid = o.itemid JOIN users u ON u.userid = o.customerid WHERE o.itemid = ?;";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), rs.getInt("itemid"), rs.getString("itemname"), customerId,
                        rs.getString("name"), rs.getInt("price"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }
}
