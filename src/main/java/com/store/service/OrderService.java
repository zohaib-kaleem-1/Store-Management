package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDAO;
import com.store.model.Order;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();

    public int getRowCount(String orderStatusToShow) throws SQLException {
        return orderDAO.getRowCount(orderStatusToShow);
    }

    public int getRowCountForUser(int customerId, String orderStatusToShow) throws SQLException {
        return orderDAO.getRowCountForUser(customerId, orderStatusToShow);
    }

    public List<Order> listOrderByCustomerId(int userId, String orderStatus, int limit, int pageIndex)
            throws SQLException {
        return orderDAO.listOrderByCustomerId(userId, orderStatus, limit, pageIndex);
    }

    public List<Order> listOrder(String orderStatus, int limit, int pageIndex)
            throws SQLException {
        return orderDAO.listOrder(orderStatus, limit, pageIndex);
    }

    public boolean updateOrderStatus(int orderid, String orderStatus) throws SQLException {
        return orderDAO.updateOrderStatus(orderid, orderStatus);
    }
}
