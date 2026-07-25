package com.store.GUI.controllers.AdminControllers.ManageItem;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.Item;
import com.store.service.ItemService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddUpdateItemController implements SceneManager.DataReceiver<Item> {
    @FXML
    private TextField itemNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label addUpdateLabel;

    @FXML
    private Button deleteButton;

    private Item currentItem;

    private String mode;

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    public void save() {
        try {
            String itemName = itemNameField.getText();
            ValidationUtil.validateItemName(itemName);
            int price = ValidationUtil.validateIntInput(priceField.getText());
            int quantity = ValidationUtil.validateIntInput(quantityField.getText());

            currentItem.setName(itemName);
            currentItem.setPrice(price);
            currentItem.setQuantity(quantity);

            if (mode.matches("update")) {
                if (new ItemService().updateItem(currentItem))
                    MessageUtil.showMessage("Item Manager", "Item Updated successfully.");
                else
                    MessageUtil.showError("Item Manager", "Error updating item");
            } else if (mode.matches("add")) {
                if (new ItemService().addItem(itemName, price, quantity))
                    MessageUtil.showMessage("Item Manager", "Item Added successfully.");
                else
                    MessageUtil.showError("Item Manager", "Error updating item");
            }

            goBack();
        } catch (

        Exception e) {
            MessageUtil.showError("Item Manager", e.getMessage());
        }
    }

    @Override
    public void setData(Item data) {
        if (data == null) {
            // It came from add item
            currentItem = new Item("", 0, 0);
            addUpdateLabel.setText("Add Item");
            mode = "add";

            deleteButton.setVisible(false);
        } else {
            // It came from update item
            currentItem = data;
            addUpdateLabel.setText("Update Item");
            mode = "update";

            updateFields();
            deleteButton.setVisible(true);
        }
    }

    public void updateFields() {
        itemNameField.setText(currentItem.getName());
        priceField.setText(String.valueOf(currentItem.getPrice()));
        quantityField.setText(String.valueOf(currentItem.getQuantity()));
    }

    @FXML
    public void delete(){
        try{
            if(new ItemService().removeItem(currentItem.getId()))
                MessageUtil.showMessage("Item Manager", "Item Deleted Successfully.");
            else
                MessageUtil.showError("Item Manager", "Error Deleting Item");

            goBack();
        }catch(Exception e){
            MessageUtil.showError("Item Manager", "Error Deleting item");
        }
    }
}