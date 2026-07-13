package com.store.GUI.controllers.BuyItem;

import com.store.Util.SceneManager;
import com.store.model.CartItem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddToCartController implements SceneManager.DataReceiver<Cart> {
    @FXML
    private TextField itemNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private TextField quantityInStoreField;

    private CartItem dataToUpdate;

    @FXML
    public void initialize() {
        itemNameField.setDisable(true);
        priceField.setDisable(true);
        quantityInStoreField.setDisable(true);
        totalPriceField.setDisable(true);
    }

    @FXML
    public void save() {

    }

    @FXML
    public void delete() {

    }

    @Override
    public void setData(CartItem data) {
        dataToUpdate = data;

    };

    public void updateFields() {
        itemNameField.setText(dataToUpdate.getItemName());
        priceField.setText(String.valueOf(dataToUpdate.getPrice()));
        quantityField.setText(String.valueOf(dataToUpdate.getQuantity()));
        quantityInStoreField.setText(String.valueOf(dataToUpdate.getQuantityInStore()));
        totalPriceField.setText(String.valueOf(dataToUpdate.getTotalPrice()));
    }
}
