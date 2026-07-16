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
    public boolean addCart(CartItem c) throws SQLException {

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
            stmt.setInt(1, c.getItemId());
            stmt.setInt(2, c.getCustomerId());
            stmt.setInt(3, c.getQuantity());
            stmt.setInt(4, c.getQuantity());
            stmt.setInt(5, c.getCustomerId());
            stmt.setInt(6, c.getItemId());

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

    /**
     * List all items of specific customer that are in cart
     * 
     * @param id ID of customer of whom data is to be fetched
     * @return Returns list of items in cart or null if error is thrown
     * @throws SQLException Sends exception to where it is called
     */
    public List<CartItem> listFromCartByCustomerId(int id) throws SQLException {
        List<CartItem> list = new ArrayList<>();

        // Select data from item and cart table using join query to show to customer
        String sql = "SELECT c.cartid, c.itemid, c.userid , c.quantity, i.itemname, i.price, i.quantity AS quantityInStore FROM cart c JOIN items i ON i.itemid = c.itemid JOIN users u ON u.userid = c.userid WHERE u.userid = ?; ";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CartItem(rs.getInt("cartid"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("userid"), "", rs.getInt("price"), rs.getInt("quantity"),
                        rs.getInt("quantityInStore")));
            }
        }

        return list;
    }
}
