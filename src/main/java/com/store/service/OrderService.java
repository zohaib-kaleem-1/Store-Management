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

    public void addOrder(Connection conn, int itemId, int customerId, int price, int quantity) throws Exception {
        // if (ordersDAO.addOrder(new Order(itemId, customerId, quantity, price))) {

        // } else {

        // }
    }

    public void removeOrder(Connection conn, int id) throws Exception {
        if (ordersDAO.removeOrder(conn, id)) {

        } else {

        }
    }

    public void updateOrder(Connection conn, Order o) throws Exception {
        if (ordersDAO.updateOrder(conn, o)) {

        } else {

        }
    }

    public void listOrder() {
        List<Order> list = ordersDAO.listFromOrder();

        if (list == null || list.isEmpty()) {

        }
    }
}
