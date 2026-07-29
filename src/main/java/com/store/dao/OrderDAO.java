package com.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.store.db.Database;
import com.store.model.Order;
import com.store.model.OrderItem;

public class OrderDAO {
    private List<OrderItem> getItemListByOrderId(int id) throws SQLException {
        List<OrderItem> itemList = new ArrayList<>();
        String sql = """
                SELECT
                	OI.ORDERITEMID,
                	OI.QUANTITY,
                	OI.PRICEOFSINGLEITEM,
                	OI.ITEMID, I.ITEMNAME
                FROM
                	ORDERITEMS OI
                	JOIN ITEMS I ON I.ITEMID = OI.ITEMID
                WHERE
                	OI.ORDERID = ?;
                                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                itemList.add(new OrderItem(rs.getInt("orderitemid"), rs.getInt("itemid"), rs.getString("itemname"),
                        rs.getInt("priceofsingleitem"), rs.getInt("quantity")));
            }
        }

        return itemList;
    }

    public int getRowCountForUser(int customerId, String orderStatus, String orderId) throws SQLException {
        String sql = """
                SELECT count(orderid) FROM orders WHERE userid = ? AND orderStatus ILIKE ? AND CAST (orderid AS TEXT) LIKE ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, "%" + orderStatus + "%");
            stmt.setString(3, "%" + orderId + "%");

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt("count");
            return 0;
        }
    }

    public int getRowCount(String orderStatus) throws SQLException {
        String sql = """
                SELECT count(orderid) FROM orders WHERE orderStatus ILIKE ?
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + orderStatus + "%");

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt("count");
            return 0;
        }
    }

    public List<Order> listOrderByCustomerId(int userId, String orderId, String orderStatus, int limit, int pageIndex)
            throws SQLException {
        List<Order> orderList = new ArrayList<>();

        String sql = """
                SELECT
                	ORDERID,
                	ADDRESS,
                	TOTALPRICE,
                	ORDERSTATUS,
                	BOUGHT_AT
                FROM
                	ORDERS
                WHERE
                    userid = ?
                    AND orderstatus ILIKE ?
                    AND CAST(orderId AS TEXT) ILIKE ?
                ORDER BY orderid
                LIMIT ?
                OFFSET ?;
                                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, "%" + orderStatus + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, pageIndex * limit);
            stmt.setString(5, "%" + orderId + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderList.add(new Order(rs.getInt("orderid"), userId, "",
                        rs.getInt("totalPrice"), null, rs.getTimestamp("bought_at"), rs.getString("orderstatus"),
                        rs.getString("address")));
            }

            for (Order i : orderList) {
                i.setItemList(getItemListByOrderId(i.getOrderId()));
            }
        }

        return orderList;
    }

    public List<Order> listOrder(String orderStatus, int limit, int pageIndex)
            throws SQLException {
        List<Order> orderList = new ArrayList<>();

        String sql = """
                SELECT
                	O.ORDERID,
                    O.USERID,
                	O.ADDRESS,
                	O.TOTALPRICE,
                	O.ORDERSTATUS,
                	O.BOUGHT_AT,
                    U.name
                FROM
                	ORDERS O
                JOIN USERS U ON U.USERID = O.USERID
                WHERE
                    orderstatus ILIKE ?
                ORDER BY orderid
                LIMIT ?
                OFFSET ?
                                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + orderStatus + "%");
            stmt.setInt(2, limit);
            stmt.setInt(3, pageIndex * limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderList.add(new Order(rs.getInt("orderid"), rs.getInt("userid"), rs.getString("name"),
                        rs.getInt("totalPrice"), null, rs.getTimestamp("bought_at"), rs.getString("orderstatus"),
                        rs.getString("address")));
            }

            for (Order i : orderList) {
                i.setItemList(getItemListByOrderId(i.getOrderId()));
            }
        }

        return orderList;
    }

    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = """
                UPDATE ORDERS
                SET orderstatus = ?
                WHERE orderid = ?;
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    public int getTotalSpent(int orderId) throws SQLException {
        String sql = """
                SELECT totalprice FROM ORDERS
                WHERE orderid = ?;
                """;

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();
            int totalPrice = 0;
            while (rs.next()) {
                totalPrice += rs.getInt("totalprice");
            }

            return totalPrice;
        }
    }
}
