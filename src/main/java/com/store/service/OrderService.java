package com.store.service;

import java.util.List;

import com.store.dao.OrdersDAO;
import com.store.model.Order;

public class OrderService {
    OrdersDAO ordersDAO;

    public OrderService() {
        this.ordersDAO = new OrdersDAO();
    }

    public void addOrder(int itemId, int customerId, int price, int quantity) {
        if (ordersDAO.addOrder(new Order(itemId, customerId, quantity, price))) {

        } else {

        }
    }

    public void removeOrder(int id) {
        if (ordersDAO.removeOrder(id)) {

        } else {

        }
    }

    public void updateOrder(Order o) {
        if (ordersDAO.updateOrder(o)) {

        } else {

        }
    }

    public void listOrder() {
        List<Order> list = ordersDAO.listFromOrder();

        if (list == null || list.isEmpty()) {

        }
    }
}
