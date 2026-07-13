package com.store.GUI.controllers.AdminControllers.ManageItem;

import java.sql.Connection;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.db.Database;
import com.store.model.Item;
import com.store.service.ItemService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateItemController implements SceneManager.DataReceiver<Item> {
    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    private Item itemToUpdate;

    @FXML
    public void goBack() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/manageitemview.fxml", "Manage Admin");
    }

    @FXML
    public void save() {
        String name = nameField.getText();
        int price;
        int quantity;

        try (Connection conn = Database.getConnection()) {
            ValidationUtil.validateName(name);
            price = ValidationUtil.validateIntInput(priceField.getText());
            quantity = ValidationUtil.validateIntInput(quantityField.getText());

            itemToUpdate.setName(name);
            itemToUpdate.setPrice(price);
            itemToUpdate.setQuantity(quantity);

            ItemService itemService = new ItemService();
            itemService.updateItem(conn, itemToUpdate);

            MessageUtil.showMessage("Store", "Item updated successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Invalid Input", e.getMessage());
            return;
        }
    }

    @FXML
    public void updateFields() {
        nameField.setText(itemToUpdate.getName());
        priceField.setText(String.valueOf(itemToUpdate.getPrice()));
        quantityField.setText(String.valueOf(itemToUpdate.getQuantity()));
    }

    @FXML
    public void delete() {
        ItemService itemService = new ItemService();
        try (Connection conn = Database.getConnection()) {
            itemService.removeItem(conn, itemToUpdate.getName());
            MessageUtil.showMessage("Delete Item", "Item deleted Successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Update Item", e.getMessage());
        }
    }

    @Override
    public void setData(Item data) {
        this.itemToUpdate = data;
        updateFields();
    }
}
