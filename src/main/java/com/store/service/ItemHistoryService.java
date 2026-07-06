package com.store.service;

import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.dao.ItemHistoryDAO;
import com.store.model.ItemHistory;

public class ItemHistoryService {
    ItemHistoryDAO itemHistoryDAO;

    public ItemHistoryService() {
        this.itemHistoryDAO = new ItemHistoryDAO();
    }

    public void addItemHistory(int itemId, int customerId, int quantity, int price) {
        if (itemHistoryDAO.addItemHistory(new ItemHistory(itemId, customerId, quantity, price))) {
            Helper.printColored("Item History Updated Successfully.", "green");
        } else {
            Helper.printColored("Could not update item history.", "red");
        }
    }

    public void removeItemHistory(ItemHistory i) {
        if (itemHistoryDAO.removeItemHistory(i.getId())) {
            Helper.printColored("Item Removed from History Successfully.", "green");
        } else {
            Helper.printColored("Could not remove from item history.", "red");
        }
    }

    public void updateItemHistory(ItemHistory i) {
        if (itemHistoryDAO.updateItemHistory(i)) {
            Helper.printColored("Item History Updated Successfully.", "green");
        } else {
            Helper.printColored("Could not update item history.", "red");
        }
    }

    public void displayItemHistory() {
        List<ItemHistory> history = itemHistoryDAO.listItem();

        if (history.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "blue");
            return;
        }

        Helper.printList(history);
    }

    public void displayItemHistoryByItemId(int id) {
        List<ItemHistory> history = itemHistoryDAO.GetItemHistoryByItemId(id);

        if (history.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "blue");
            return;
        }

        Helper.printList(history);
        int totalPrice = 0;

        for (ItemHistory itemHistory : history) {
            totalPrice += itemHistory.getPrice();
        }

        Helper.printColored("Total Price: " + totalPrice, "green");
    }

    public void displayItemHistoryByCustomerId(int id) {
        List<ItemHistory> history = itemHistoryDAO.GetItemHistoryByCustomerId(id);

        if (history.isEmpty()) {
            Helper.printColored("NO RECORD FOUND FOR THIS CUSTOMER.", "red");
            return;
        }

        Helper.printList(history);

        int totalPrice = 0;

        for (ItemHistory i : history) {
            totalPrice += i.getPrice();
        }

        Helper.printColored("Total Price: " + totalPrice, "green");
    }
}