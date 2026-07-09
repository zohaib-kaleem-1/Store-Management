package com.store.service;

import java.util.List;

import com.store.dao.ItemDAO;
import com.store.model.Item;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public void addItem(String itemName, int itemPrice, int quantity) {
        if (itemDAO.addItem(new Item(itemName, itemPrice, quantity))) {
        } else {
        }
    }

    public void removeItem(String itemName) {
        if (itemDAO.removeItem(itemName)) {
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

    public void updateItem(Item a) {
        if (itemDAO.updateItem(a)) {
        } else {
        }
    }

    public void display() {
        List<Item> itemList = itemDAO.listItems();

        if (itemList.isEmpty()) {
            return;
        }
    }

    public boolean displayAvailableItems() {
        List<Item> itemList = itemDAO.listAvailableItems();

        if (itemList == null || itemList.isEmpty()) {
            return false;
        }
        return true;
    }
}
