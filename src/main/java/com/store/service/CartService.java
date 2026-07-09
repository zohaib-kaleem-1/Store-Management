package com.store.service;

import java.util.ArrayList;
import java.util.List;

import com.store.dao.CartDAO;
import com.store.model.Cart;

public class CartService {
    CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    public void addToCart(Cart c) {
        if (cartDAO.addCart(c)) {

        } else {

        }
    }

    public void updateCart(Cart c) {
        if (cartDAO.updateCart(c)) {

        } else {

        }
    }

    public void removeCart(int id) {
        if (cartDAO.removeCart(id)) {

        } else {

        }
    }

    public void listFromCart() {
        List<Cart> list = cartDAO.listFromCart();

        if (list == null || list.isEmpty()) {

        }
    }

    public void listFromCartByCustomerId(int id) {
        List<Cart> list = cartDAO.listFromCartByCustomerId(id);

        if (list == null || list.isEmpty()) {

        }
    }

    public void listFromCartByItemId(int id) {
        List<Cart> list = cartDAO.listFromCartByItemId(id);

        if (list == null || list.isEmpty()) {

        }
    }
}
