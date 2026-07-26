package com.store.GUI.controllers.CustomerControllers.Cart;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.model.CartItem;
import com.store.model.Item;
import com.store.service.CartService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateCartController implements SceneManager.DataReceiver<CartItem> {
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField quantityInStoreField;
    @FXML
    private TextField totalPriceField;

    private CartItem itemToUpdate;

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void initialize() {
        itemNameField.setDisable(true);
        priceField.setDisable(true);
        totalPriceField.setDisable(true);
        quantityInStoreField.setDisable(true);

        quantityField.textProperty().addListener(e -> {
            updateTotalPrice();
        });
    }

    @Override
    public void setData(CartItem data) {
        try {
            if (data == null)
                MessageUtil.showError("Buy Item Manager", "Could Not Load Item Data");

            currentItemToCart = data;
            updateFields();
        } catch (Exception e) {
            MessageUtil.showError("Buy Item Manager", e.getMessage());
        }
    }

    private void updateFields() {
        itemNameField.setText(currentItemToCart.getName());
        priceField.setText(String.valueOf(currentItemToCart.getPrice()));
        quantityInStoreField.setText(String.valueOf(currentItemToCart.getQuantity()));

    }

    private void updateTotalPrice() {
        try {
            int quantiy = ValidationUtil.validateIntInput(quantityField.getText());
            int quantityInStore = currentItemToCart.getQuantity();

            if (quantityInStore < quantiy)
                throw new Exception("Quanity must be less than or equal to quantity in store");
            int price = Integer.parseInt(priceField.getText());
            int totalPrice = quantiy * price;
            totalPriceField.setText(String.valueOf(totalPrice));
        } catch (Exception e) {
            MessageUtil.showError("Buy Item Manager", e.getMessage());
        }
    }
}
