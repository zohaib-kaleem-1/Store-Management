package com.store.dao;

import com.store.db.Database;
import com.store.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public boolean addOrder(Order o) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO orders (itemid, customerid, quantity, price) VALUES (?,?,?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, o.getItemId());
            stmt.setInt(2, o.getCustomerId());
            stmt.setInt(3, o.getQuantity());
            stmt.setInt(4, o.getPrice());

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean updateOrder(Order o) {
        try (Connection conn = Database.getConnection()) {
            String sql = "UPDATE orders SET itemid = ?, customerid = ?, quantity = ?,price = ? WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, o.getItemId());
            stmt.setInt(2, o.getCustomerId());
            stmt.setInt(3, o.getQuantity());
            stmt.setInt(3, o.getPrice());
            stmt.setInt(4, o.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean removeOrder(int id) {
        try (Connection conn = Database.getConnection()) {
            String sql = "DELETE from orders WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public List<Order> listFromOrder() {
        List<Order> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM orders;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                        new Order(rs.getInt("id"), rs.getInt("itemid"), rs.getInt("customerid"), rs.getInt("quantity"),
                                rs.getInt("price")));
            }
        } catch (Exception e) {

        }
        return list;
    }
}
