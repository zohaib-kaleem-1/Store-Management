package com.store.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.store.dao.StoreDAO;
import com.store.model.Store;

public class StoreService {
    StoreDAO storeDAO;

    public StoreService() {
        this.storeDAO = new StoreDAO();
    }

    public Store getStoreData() throws SQLException {
        return storeDAO.getStoreData();
    }

    public boolean updateStoreName(String name) throws SQLException {
        return storeDAO.updateStoreName(name);
    }

    public boolean incraeseBalance(Connection conn, int balance) throws SQLException {
        return storeDAO.increaseBalance(conn, balance);
    }

    public boolean decreaseBalance(Connection conn, int balance) throws SQLException {
        return storeDAO.decreaseBalance(conn, balance);
    }

    public int getBalance() throws SQLException {
        return storeDAO.getBalance();
    }
}
