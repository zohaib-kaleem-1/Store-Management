package com.store.service;

import java.sql.Connection;
import java.util.List;

import com.store.dao.OrdersDAO;
import com.store.model.Order;

public class OrderService {
    OrdersDAO ordersDAO;

    public OrderService() {
        this.ordersDAO = new OrdersDAO();
    }

    public boolean addOrder(Connection conn, int itemId, int customerId, int price, int quantity) throws Exception {
        return ordersDAO.addOrder(conn, itemId, customerId, price, quantity);
    }

    public boolean removeOrder(Connection conn, int id) throws Exception {
        return ordersDAO.removeOrder(conn, id);
    }

    public boolean updateOrder(Connection conn, Order o) throws Exception {
        return ordersDAO.updateOrder(conn, o);
    }

    public List<Order> listOrder() {
        return ordersDAO.listFromOrder();
    }

    public List<Order> listOrderByItemId(int itemid) {
        return ordersDAO.listFromOrderByItemId(itemid);
    }

    public List<Order> listOrderByCustomerId(int itemid) {
        return ordersDAO.listFromOrderByCustomerId(itemid);
    }
}
