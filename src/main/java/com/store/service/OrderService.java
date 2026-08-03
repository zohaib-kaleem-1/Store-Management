package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDAO;
import com.store.model.Order;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();

    public int getRowCount(String orderStatusToShow, String orderId) throws SQLException {
        return orderDAO.getRowCount(orderStatusToShow, orderId);
    }

    public int getRowCountForUser(int customerId, String orderStatusToShow, String orderId) throws SQLException {
        return orderDAO.getRowCountForUser(customerId, orderStatusToShow, orderId);
    }

    public List<Order> listOrderByCustomerId(int userId, String orderId, String orderStatus, int limit, int pageIndex)
            throws SQLException {
        return orderDAO.listOrderByCustomerId(userId, orderId, orderStatus, limit, pageIndex);
    }

    public List<Order> listOrder(String orderId, String orderStatus, int limit, int pageIndex)
            throws SQLException {
        return orderDAO.listOrder(orderId, orderStatus, limit, pageIndex);
    }

    public boolean updateOrderStatus(int orderid, String orderStatus) throws SQLException {
        return orderDAO.updateOrderStatus(orderid, orderStatus);
    }

    public int getTotalSpent(int userId) throws SQLException {
        return orderDAO.getTotalSpent(userId);
    }

    public double getTotalRevenue() throws SQLException {
        return orderDAO.getTotalRevenue();
    }
}
