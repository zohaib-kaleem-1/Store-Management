package com.store.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.store.Util.SessionManager;
import com.store.db.Database;

public class Transaction {
    public static boolean buyItem(String address) throws Exception {
        int currentUserId = SessionManager.getUser().getId();
        int totalPrice = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Start connection
            conn = Database.getConnection();
            // Begin transaction
            conn.setAutoCommit(false);

            // Verifying Limit
            String sql = """
                        Select c.quantity, i.quantity, i.price, i.quantity as quantityinstore from cart c join items i on c.itemid = i.itemid Where c.userid = ? for update;
                    """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            rs = stmt.executeQuery();

            boolean cartHasItems = false;
            while (rs.next()) {
                cartHasItems = true;
                int quantity = rs.getInt("quantity");
                int quantityinstore = rs.getInt("quantityinstore");
                int price = rs.getInt("price");
                totalPrice += quantity * price;

                if (quantity > quantityinstore) {
                    throw new Exception("Quantity can't be greater than quantity in store");
                }
            }

            if (!cartHasItems)
                throw new Exception("Cart is empty");

            // Add Order row
            sql = """
                    INSERT INTO ORDERS (userid, address, totalPrice) VALUES (?, ?, ?) Returning orderId;
                    """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            stmt.setString(2, address);
            stmt.setInt(3, totalPrice);
            rs = stmt.executeQuery();

            int orderId = -1;
            if (rs.next())
                orderId = rs.getInt("orderid");

            // Adding OrderItems
            sql = """
                    INSERT INTO
                    	ORDERITEMS (ORDERID, QUANTITY, PRICEOFSINGLEITEM, ITEMID)
                    SELECT
                    	?,
                    	C.QUANTITY,
                    	I.PRICE,
                    	C.ITEMID
                    FROM
                    	CART C
                    	JOIN ITEMS I ON I.ITEMID = C.ITEMID
                    WHERE
                    	C.USERID = ?;
                                        """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.setInt(2, currentUserId);
            stmt.execute();

            // update quantity in item table
            sql = """
                    UPDATE ITEMS
                    SET
                    	QUANTITY = ITEMS.QUANTITY - c.quantity
                    FROM
                    	CART c
                    WHERE
                    	C.ITEMID = ITEMS.ITEMID
                    	AND C.USERID = ?;
                                        """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            stmt.execute();

            // clear cart
            sql = """
                    DELETE FROM CART where userid = ?;
                    """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            stmt.execute();

            // Update balance
            sql = """
                    Update store
                    set balance = balance + ?;
                    """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, totalPrice);
            stmt.execute();

            conn.commit();

            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            if (conn != null)
                conn.close();

            if (stmt != null)
                stmt.close();

            if (rs != null)
                rs.close();
        }
    }

    public static boolean cancelOrder(int orderId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Add connection
            conn = Database.getConnection();

            // Start transaction
            conn.setAutoCommit(false);

            // reverse items quantity
            String sql = """
                    UPDATE items
                    SET quantity = items.quantity + oi.quantity
                    FROM orderitems oi
                    WHERE oi.orderid = ?
                    AND oi.itemid = items.itemid;
                    """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            System.out.println("updated item quantity");

            // update account balance
            sql = """
                    Update Store set balance = balance - (Select totalprice from orders where orderid = ?);
                    """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            System.out.println("updated balance");

            // delete order from order table
            sql = """
                    DELETE FROM orders where orderid = ?;
                    """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            System.out.println("update order");

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            if (conn != null)
                conn.close();

            if (stmt != null)
                stmt.close();

            if (rs != null)
                rs.close();
        }
    }
}
