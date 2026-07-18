package com.store.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.dao.ItemDAO;
import com.store.model.Item;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public boolean addItem(String itemName, int itemPrice, int quantity) throws SQLException {
        return itemDAO.addItem(new Item(itemName, itemPrice, quantity));
    }

    public boolean removeItem(int itemId) throws Exception {
        return itemDAO.removeItem(itemId);
    }

    public boolean updateItem(Item a) throws Exception {
        return itemDAO.updateItem(a);
    }

    public List<Item> display(String itemName, int limit, int pageIndex) throws SQLException {
        return itemDAO.listItems(itemName, limit, pageIndex);
    }

    public List<Item> displayAvailableItems() throws SQLException {
        return itemDAO.listAvailableItems();
    }

    public boolean increaseQuantity(Connection conn, int id, int quantity) throws SQLException {
        return itemDAO.increaseQuantity(conn, id, quantity);
    }

    public boolean decreaseQuantity(Connection conn, int id, int quantity) throws SQLException {
        return itemDAO.increaseQuantity(conn, id, quantity);
    }

    public int getRowCount(String itemName) throws SQLException {
        return itemDAO.getRowCount(itemName);
    }
}
