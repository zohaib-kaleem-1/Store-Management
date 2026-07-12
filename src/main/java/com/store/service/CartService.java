package com.store.service;

import java.sql.Connection;
import java.util.List;

import com.store.dao.CartDAO;
import com.store.model.CartItem;

public class CartService {
    CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    public boolean addToCart(Connection conn, CartItem c) throws Exception {
        return cartDAO.addCart(conn, c);
    }

    public boolean updateCart(Connection conn, CartItem c) throws Exception {
        return cartDAO.updateCart(conn, c);
    }

    public boolean removeCart(Connection conn, int id) throws Exception {
        return cartDAO.removeCart(conn, id);
    }

    public List<CartItem> listFromCart() {
        return cartDAO.listFromCart();
    }

    public List<CartItem> listFromCartByCustomerId(int id) {
        return cartDAO.listFromCartByCustomerId(id);
    }

    public List<CartItem> listFromCartByItemId(int id) {
        return cartDAO.listFromCartByItemId(id);
    }
}
