package com.store.dao;

import com.store.db.Database;
import com.store.model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public boolean addCart(Cart c) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO cart (itemid, customerid, quantity) VALUES (?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, c.getItemId());
            stmt.setInt(2, c.getCustomerId());
            stmt.setInt(3, c.getQuantity());

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean updateCart(Cart c) {
        try (Connection conn = Database.getConnection()) {
            String sql = "UPDATE cart SET itemid = ?, customerid = ?, quantity = ? WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, c.getItemId());
            stmt.setInt(2, c.getCustomerId());
            stmt.setInt(3, c.getQuantity());
            stmt.setInt(4, c.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean removeCart(int id) {
        try (Connection conn = Database.getConnection()) {
            String sql = "DELETE from cart WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            return false;
        }
    }

    public List<Cart> listFromCart() {
        List<Cart> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM CART;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                        new Cart(rs.getInt("id"), rs.getInt("itemid"), rs.getInt("customerid"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Cart> listFromCartByCustomerId(int id) {
        List<Cart> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM CART WHERE customerid = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                        new Cart(rs.getInt("id"), rs.getInt("itemid"), rs.getInt("customerid"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Cart> listFromCartByItemId(int id) {
        List<Cart> list = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM CART WHERE itemid = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(
                        new Cart(rs.getInt("id"), rs.getInt("itemid"), rs.getInt("customerid"), rs.getInt("quantity")));
            }
        } catch (Exception e) {

        }
        return list;
    }

}
