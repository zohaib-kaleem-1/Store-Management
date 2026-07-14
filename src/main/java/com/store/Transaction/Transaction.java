package com.store.Transaction;

import com.store.Util.SessionManager;
import com.store.db.Database;
import com.store.model.CartItem;
import com.store.service.BalanceService;
import com.store.service.CartService;
import com.store.service.ItemService;
import com.store.service.OrderService;

import java.sql.*;
import java.util.List;

public class Transaction {
    public static boolean customerBuyItems() {
        CartService cartService = new CartService();
        ItemService itemService = new ItemService();
        OrderService orderService = new OrderService();

        Connection conn = null;
        try (BalanceService balanceService = new BalanceService()) {
            int currentBalance = balanceService.getBalance();
            conn = Database.getConnection();
            List<CartItem> cartList = cartService.listFromCartByCustomerId(SessionManager.getUser().getId());
            int customerid = SessionManager.getUser().getId();

            for (CartItem i : cartList) {
                if (i.getQuantity() > i.getQuantityInStore())
                    throw new Exception("Quantity of " + i.getItemName() + " must be reduced to maximum of "
                            + i.getQuantityInStore());
            }

            conn.setAutoCommit(false);

            for (CartItem i : cartList) {
                itemService.decreaseQuantity(conn, i.getItemId(), i.getQuantity());
                cartService.removeCart(conn, i.getId());
                orderService.addOrder(conn, i.getItemId(), customerid, i.getPrice(), i.getQuantity());
                currentBalance += i.getTotalPrice();
            }

            conn.commit();

            balanceService.setBalance(currentBalance);
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

    public static boolean adminBuysItem() {
        CartService cartService = new CartService();
        ItemService itemService = new ItemService();
        OrderService orderService = new OrderService();

        Connection conn = null;
        try (BalanceService balanceService = new BalanceService()) {
            int currentBalance = balanceService.getBalance();
            conn = Database.getConnection();
            List<CartItem> cartList = cartService.listFromCartByCustomerId(SessionManager.getUser().getId());
            int customerid = SessionManager.getUser().getId();

            int totalPriceToBuy = 0;
            for (CartItem i : cartList) {
                totalPriceToBuy += i.getTotalPrice();
            }

            System.out.println("total price " + totalPriceToBuy);

            if (totalPriceToBuy > balanceService.getBalance()) {
                throw new Exception("Balance is less to buy these items");
            }

            conn.setAutoCommit(false);

            for (CartItem i : cartList) {
                itemService.increaseQuantity(conn, i.getItemId(), i.getQuantity());
                cartService.removeCart(conn, i.getId());
                orderService.addOrder(conn, i.getItemId(), customerid, i.getPrice(), i.getQuantity());
                currentBalance -= i.getTotalPrice();

                System.out.println("buying item " + i.getItemName());
            }

            conn.commit();

            balanceService.setBalance(currentBalance);
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
