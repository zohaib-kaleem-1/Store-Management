package com.store.dao;

import com.store.db.Database;
import com.store.model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CartDAO
 * 
 * Contains function to CRUD with cart table
 */

public class CartDAO {
    /**
     * ADD a new row whenevert user adds something to cart
     * 
     * @param c model that contains data to add to database
     * @return true if row is added false if could not add row
     * @throws SQLException throws exception if any excception is found which then
     *                      will
     *                      be printed
     */
    public boolean addCart(int itemId, int customerId, int quantity) throws SQLException {

        /**
         * Used on conflict clause to handle already present same cart item by same user
         * Insert the item to cart and if already present update and add the quantity
         */
        String sql = """
                INSERT INTO CART (ITEMID, USERID, QUANTITY)
                VALUES (?, ?, ?)
                ON CONFLICT (USERID, ITEMID)
                DO UPDATE
                SET
                	QUANTITY = CART.QUANTITY + ?
                WHERE
                	cart.USERID = ?
                	AND cart.ITEMID = ?;
                                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            /**
             * Insert values into query preventing the SQL Injection
             */
            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.setInt(5, customerId);
            stmt.setInt(6, itemId);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * If users updates quantity of any item in cart this function update it in
     * database
     * 
     * @param id       contains the id of row that contains specific cart item
     * @param quantity contains the new quantity of item
     * @return return true if successfully updated the quantity
     * @throws SQLException throws exception to handle any exception in process and
     *                      handle it where it is called
     */
    public boolean updateQuantity(int id, int quantity) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE cartid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, quantity);

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * If user want to remove something from cart
     * 
     * @param id contains id of row from database
     * @return true if item was removed from the cart
     * @throws SQLException
     */
    public boolean removeCart(int id) throws SQLException {
        String sql = "DELETE from cart WHERE cartid = ?;";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public int getRowCount(int customerId, String itemName) throws SQLException {
        String sql = """
                SELECT
                	COUNT(C.CARTID)
                FROM
                	CART C
                	JOIN ITEMS I ON I.ITEMID = C.ITEMID
                WHERE
                	C.USERID = ?
                	AND I.ITEMNAME LIKE ?;
                                                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, "%" + itemName + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt("count");
            return 0;
        }
    }

    public List<CartItem> listFromCartByCustomerId(int customerId, String itemName, int limit, int pageIndex)
            throws SQLException {
        List<CartItem> cartList = new ArrayList<>();

        // Select data from item and cart table using join query to show to customer
        String sql = """
                SELECT
                    c.cartId,
                    c.quantity,
                    c.itemId,
                    i.itemName,
                    i.price,
                    i.quantity AS quantityAvailable
                FROM
                    cart c
                JOIN
                    items i
                ON
                    i.itemid = c.itemid
                WHERE
                    c.userid = ?
                AND
                    i.itemName ILIKE ?
                ORDER BY c.cartid
                LIMIT ?
                OFFSET ? ;
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, "%" + itemName + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, limit * pageIndex);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cartList.add(new CartItem(
                        rs.getInt("cartid"), rs.getString("itemName"), rs.getInt("itemid"), rs.getInt("quantity"),
                        rs.getInt("quantityAvailable"), rs.getInt("price")));
            }
        }

        return cartList;
    }

    /**
     * When user buys, clear his cart in one command
     * 
     * @param customerId id of user whose cart is to be cleared
     * @throws SQLException
     */
    public boolean removeCartByUserId(int customerId) throws SQLException {
        String sql = "DELETE from cart WHERE userid = ?;";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }
}
