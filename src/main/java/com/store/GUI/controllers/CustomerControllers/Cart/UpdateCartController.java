package com.store.GUI.controllers.CustomerControllers.Cart;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.CartItem;
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
                MessageUtil.showError("Cart Manager", "Could Not Load Item Data");

            itemToUpdate = data;
            updateFields();
        } catch (Exception e) {
            MessageUtil.showError("Cart Manager", e.getMessage());
        }
    }

    private void updateFields() {
        itemNameField.setText(itemToUpdate.getItemName());
        priceField.setText(String.valueOf(itemToUpdate.getPriceOfEachItem()));
        quantityInStoreField.setText(String.valueOf(itemToUpdate.getQuantityInStore()));
        quantityField.setText(String.valueOf(itemToUpdate.getQuantity()));
    }

    private void updateTotalPrice() {
        try {
            if (quantityField.getText().isEmpty())
                return;

            int quantiy = ValidationUtil.validateIntInput(quantityField.getText());
            int quantityInStore = itemToUpdate.getQuantityInStore();

            if (quantityInStore < quantiy)
                throw new Exception("Quanity must be less than or equal to quantity in store");

            int price = Integer.parseInt(priceField.getText());
            int totalPrice = quantiy * price;
            totalPriceField.setText(String.valueOf(totalPrice));
        } catch (Exception e) {
            MessageUtil.showError("Cart Manager", e.getMessage());
        }
    }

    @FXML
    public void save() {
        try {
            int quantity = ValidationUtil.validateIntInput(quantityField.getText());
            if (quantity > itemToUpdate.getQuantityInStore())
                throw new Exception("Quantity Must be less then or equal to quantity in store");

            itemToUpdate.setQuantity(quantity);
            if (new CartService().updateCart(itemToUpdate)) {
                MessageUtil.showMessage("Cart Manager", "Cart Item Updated successfully.");
                goBack();
            } else {
                MessageUtil.showError("Cart Manager", "Could not delete");
            }
        } catch (Exception e) {
            MessageUtil.showError("Cart Manager", e.getMessage());
        }
    }

    @FXML
    public void delete() {
        try {
            if (new CartService().removeItemFromCart(itemToUpdate.getId())) {
                MessageUtil.showMessage("Cart Manager", "Item removed successfully from cart.");
                goBack();
            } else {
                MessageUtil.showError("Cart Manager", "Could not remove item from cart");
            }
        } catch (Exception e) {
            MessageUtil.showError("Cart Manager", e.getMessage());
        }
    }
}