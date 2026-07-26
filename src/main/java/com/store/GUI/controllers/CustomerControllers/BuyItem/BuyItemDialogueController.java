package com.store.GUI.controllers.CustomerControllers.BuyItem;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.model.Item;
import com.store.service.CartService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BuyItemDialogueController implements SceneManager.DataReceiver<Item> {
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

    private Item currentItemToCart;

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

    @FXML
    public void addToCart() {
        try {
            int quantityToBuy = ValidationUtil.validateIntInput(quantityField.getText());
            int quantityInStore = Integer.parseInt(quantityInStoreField.getText());

            if (quantityInStore < quantityToBuy)
                throw new Exception("Quantity must be less than quantity in store.");

            if (new CartService().addToCart(currentItemToCart.getId(), SessionManager.getUser().getId(),
                    quantityToBuy)) {
                MessageUtil.showMessage("Buy Item Manager", "Item added to cart");
                goBack();
            } else
                MessageUtil.showError("Buy Item Manager", "Failed to add item to cart");

        } catch (Exception e) {
            MessageUtil.showError("Buy Item Manager", e.getMessage());

        }
    }

    @Override
    public void setData(Item data) {
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
            int price = Integer.parseInt(priceField.getText());
            int totalPrice = quantiy * price;
            totalPriceField.setText(String.valueOf(totalPrice));
        } catch (Exception e) {
            MessageUtil.showError("Buy Item Manager", e.getMessage());
        }
    }
}
