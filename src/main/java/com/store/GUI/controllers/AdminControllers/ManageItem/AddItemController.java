package com.store.GUI.controllers.AdminControllers.ManageItem;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.service.ItemService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddItemController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void save() {
        String name = nameField.getText();
        int price, quantity;

        try {
            ValidationUtil.validateName(name);
            price = ValidationUtil.validateIntInput(priceField.getText());
            quantity = ValidationUtil.validateIntInput(quantityField.getText());

            ItemService itemService = new ItemService();
            itemService.addItem(name, price, quantity);

            MessageUtil.showMessage("Add Item", "Item Added Successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Add Item", e.getMessage());
            return;
        }
    }
}