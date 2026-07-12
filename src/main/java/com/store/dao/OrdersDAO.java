package com.store.dao;

import com.store.db.Database;
import com.store.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public boolean addOrder(Connection conn, Order o) throws Exception {
        String sql = "INSERT INTO orders (itemid, customerid, quantity, price) VALUES (?,?,?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getItemId());
            stmt.setInt(2, o.getCustomerId());
            stmt.setInt(3, o.getQuantity());
            stmt.setInt(4, o.getPrice());

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
            String sql = "SELECT * FROM orders;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            }
        } catch (Exception e) {

        }
        return list;
    }
}
