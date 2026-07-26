package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.CartDAO;
import com.store.model.CartItem;

public class CartService {
    CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    public boolean addToCart(int itemId, int customerId, int quantity) throws SQLException {
        return cartDAO.addCart(itemId, customerId, quantity);
    }

    public boolean updateCart(CartItem c) throws SQLException {
        return cartDAO.updateQuantity(c.getId(), c.getQuantity());
    }

    public boolean removeCart(int id) throws SQLException {
        return cartDAO.removeCart(id);
    }

    public List<CartItem> listFromCartByCustomerId(int customerId, String itemName, int limit, int pageIndex)
            throws SQLException {
        return cartDAO.listFromCartByCustomerId(customerId, itemName, limit, pageIndex);
    }

    public int getRowCount(int customerId, String itemName) throws SQLException {
        return cartDAO.getRowCount(customerId, itemName);
    }
}
