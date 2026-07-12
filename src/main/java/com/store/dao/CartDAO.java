package com.store.dao;

import com.store.db.Database;
import com.store.model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public boolean addCart(Connection conn, CartItem c) throws Exception {

        String sql = "INSERT INTO cart (itemid, customerid, quantity) VALUES (?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getItemId());
            stmt.setInt(2, c.getCustomerId());
            stmt.setInt(3, c.getQuantity());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public boolean updateCart(Connection conn, CartItem c) throws Exception {

        String sql = "UPDATE cart SET itemid = ?, customerid = ?, quantity = ? WHERE id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getItemId());
            stmt.setInt(2, c.getCustomerId());
            stmt.setInt(3, c.getQuantity());
            stmt.setInt(4, c.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public boolean removeCart(Connection conn, int id) throws Exception {
        String sql = "DELETE from cart WHERE id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public List<CartItem> listFromCart() {
        List<CartItem> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT c.id, c.itemid, c.customerid , c.quantity, i.itemname, i.price, i.quantity AS quantityInStore, u.name FROM cart c JOIN items i ON i.itemid = c.id JOIN users u ON u.userid = c.customerid;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CartItem(rs.getInt("id"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("customerid"), rs.getString("name"), rs.getInt("price"), rs.getInt("quantity"),
                        rs.getInt("quantityInStore")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CartItem> listFromCartByCustomerId(int id) {
        List<CartItem> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT c.id, c.itemid, c.customerid , c.quantity, i.itemname, i.price, i.quantity AS quantityInStore FROM cart c JOIN items i ON i.itemid = c.id JOIN users u ON u.userid = c.customerid WHERE u.userid = ?; ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CartItem(rs.getInt("id"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("customerid"), "", rs.getInt("price"), rs.getInt("quantity"),
                        rs.getInt("quantityInStore")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CartItem> listFromCartByItemId(int id) {
        List<CartItem> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT c.id, c.itemid, c.customerid , c.quantity, i.itemname, i.price, i.quantity AS quantityInStore, u.name  FROM cart c JOIN items i ON i.itemid = c.id JOIN users u ON u.userid = c.customerid WHERE i.itemid = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CartItem(rs.getInt("id"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("customerid"), rs.getString("name"), rs.getInt("price"), rs.getInt("quantity"),
                        rs.getInt("quantityInStore")));
            }
        } catch (Exception e) {

        }
        return list;
    }

}
