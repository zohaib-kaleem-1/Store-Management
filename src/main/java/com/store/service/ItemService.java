package com.store.service;

import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.dao.ItemDAO;
import com.store.model.Item;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public void addItem(String itemName, int itemPrice, int quantity) {
        if (itemDAO.addItem(new Item(itemName, itemPrice, quantity))) {
            Helper.printColored("New Item Added Successfully.", "green");
        } else {
            Helper.printColored("Error Adding new item.", "red");
        }
    }

    public void removeItem(String itemName) {
        if (itemDAO.removeItem(itemName)) {
            Helper.printColored("Item Removed Successfully.", "green");
        } else {
            Helper.printColored("Error Removing Item.", "red");
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
            Helper.printColored("Item Updated Successfully.", "green");
        } else {
            Helper.printColored("Error Updating Item", "red");
        }
    }

    public void display() {
        List<Item> itemList = itemDAO.listItems();

        if (itemList.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "red");
            return;
        }

        Helper.printList(itemList);

    }

    public boolean displayAvailableItems() {
        List<Item> itemList = itemDAO.listAvailableItems();

        if (itemList == null || itemList.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "red");
            return false;
        }

        Helper.printList(itemList);
        return true;
    }
}
