package com.store.service;

import java.sql.Connection;
import java.util.List;

import com.store.dao.ItemDAO;
import com.store.model.Item;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public boolean addItem(String itemName, int itemPrice, int quantity) throws Exception {
        return itemDAO.addItem(new Item(itemName, itemPrice, quantity));
    }

    public void removeItem(Connection conn, String itemName) throws Exception {
        if (itemDAO.removeItem(conn, itemName)) {
        } else {
        }
    }

    public boolean findItemByItemName(String itemName) {
        if (itemDAO.getItemByItemName(itemName) != null)
            return true;
        else
            return false;
    }

    public Item getItemByItemName(String itemName) {
        return itemDAO.getItemByItemName(itemName);
    }

    public void updateItem(Connection conn, Item a) throws Exception {
        if (itemDAO.updateItem(conn, a)) {
        } else {
        }
    }

    public List<Item> display() {
        return itemDAO.listItems();
    }

    public List<Item> displayAvailableItems() {
        return itemDAO.listAvailableItems();
    }

    public boolean increasePrice(Connection conn, int itemId, int price) throws Exception {
        return itemDAO.updatePrice(conn, itemId, price);
    }

    public boolean decreasePrice(Connection conn, int itemId, int price) throws Exception {
        return itemDAO.updatePrice(conn, itemId, -1 * price);
    }

    public boolean increaseQuantity(Connection conn, int itemId, int quantity) throws Exception {
        return itemDAO.updateQuantity(conn, itemId, quantity);
    }

    public boolean decreaseQuantity(Connection conn, int itemId, int quantity) throws Exception {
        return itemDAO.updateQuantity(conn, itemId, -1 * quantity);
    }

    public boolean updateName(Connection conn, int itemId, String name) throws Exception {
        return itemDAO.updateName(conn, itemId, name);
    }
}
