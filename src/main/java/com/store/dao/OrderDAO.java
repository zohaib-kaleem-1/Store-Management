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
    public List<Order> listOrder() throws SQLException {
        List<Order> orderList = new ArrayList<>();

        String sql = """
                SELECT
                	O.ORDERID,
                	O.USERID,
                	O.ADDRESS,
                	O.TOTALPRICE,
                	O.ORDERSTATUS,
                	O.BOUGHT_AT,
                	U.NAME
                FROM
                	ORDERS O
                	JOIN USERS U ON U.USERID = O.USERID;
                                """;

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

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

    private List<OrderItem> getItemListByOrderId(int id) throws SQLException {
        List<OrderItem> itemList = new ArrayList<>();
        String sql = """
                SELECT
                	OI.ORDERITEMID,
                	OI.QUANTITY,
                	OI.PRICEOFSINGLEITEM,
                	OI.ITEMID, I.ITEMNAME
                FROM
                	ORDERITEM OI
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
}
