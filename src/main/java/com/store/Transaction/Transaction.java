package com.store.Transaction;

import com.store.Util.SessionManager;
import com.store.db.Database;
import com.store.model.CartItem;
import com.store.service.AdminService;
import com.store.service.CartService;
import com.store.service.ItemService;
import com.store.service.OrderService;

import java.sql.*;
import java.util.List;

public class Transaction {
    public boolean buyItems() {
        if (!SessionManager.getUser().getRole().toLowerCase().equals("customer"))
            return false;

        CartService cartService = new CartService();

        try (Connection conn = Database.getConnection()) {
            List<CartItem> cartList = cartService.listFromCartByCustomerId(SessionManager.getUser().getId());
            int customerid = SessionManager.getUser().getId();

            conn.setAutoCommit(false);
            for (CartItem i : cartList) {

            }

            conn.commit();
        } catch (Exception e) {

        }

        return true;
    }
}
