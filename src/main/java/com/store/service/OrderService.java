package com.store.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDAO;
import com.store.model.Order;

public class OrderService {
    OrderDAO ordersDAO;

    public OrderService() {
        this.ordersDAO = new OrderDAO();
    }

    public boolean addOrder(Connection conn, int itemId, int customerId, int price, int quantity) throws Exception {
        return ordersDAO.addOrder(conn, itemId, customerId, price, quantity);
    }

    public List<Order> listOrder() throws SQLException {
        return ordersDAO.listOrder();
    }

    public List<Order> listOrderByCustomerId(int itemid) throws SQLException {
        return ordersDAO.listOrderByCustomerId(itemid);
    }
}
