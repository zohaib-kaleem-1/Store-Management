package com.store.Transaction;

import com.store.Util.SessionManager;
import com.store.db.Database;
import com.store.model.CartItem;
import com.store.service.CartService;
import com.store.service.ItemService;
import com.store.service.OrderService;

import java.sql.*;
import java.util.List;

public class Transaction {
    public static boolean buyItems() {
        if (!SessionManager.getUser().getRole().toLowerCase().equals("customer"))
            return false;

        CartService cartService = new CartService();
        ItemService itemService = new ItemService();
        OrderService orderService = new OrderService();

        Connection conn = null;
        try {
            conn = Database.getConnection();
            List<CartItem> cartList = cartService.listFromCartByCustomerId(SessionManager.getUser().getId());
            int customerid = SessionManager.getUser().getId();

            conn.setAutoCommit(false);

            for (CartItem i : cartList) {
                orderService.addOrder(conn, i.getItemId(), customerid, i.getPrice(), i.getQuantity());
                itemService.decreaseQuantity(conn, i.getItemId(), i.getQuantity());
                cartService.removeCart(conn, i.getId());

            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rx) {
                    System.out.println("Error: " + rx.getMessage());
                }
            }

            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        return true;
    }
}
