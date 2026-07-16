package com.store.GUI.controllers.BuyItem;

import java.sql.Connection;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.db.Database;
import com.store.model.CartItem;
import com.store.model.Item;
import com.store.service.CartService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddToCartController implements SceneManager.DataReceiver<Item> {
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

    private CartItem cartItem;

    @FXML
    public void initialize() {
        itemNameField.setDisable(true);
        priceField.setDisable(true);
        quantityInStoreField.setDisable(true);
        totalPriceField.setDisable(true);
    }

    @FXML
    public void save() {
        calculateTotalPrice();

        try (Connection conn = Database.getConnection()) {
            int quantity = ValidationUtil.validateIntInput(quantityField.getText());

            if (SessionManager.getUser().getRole().toLowerCase().equals("customer"))
                if (cartItem.getQuantityInStore() < quantity)
                    throw new Exception("Quantity must be less than available quantity.");

            cartItem.setQuantity(quantity);
            CartService cartService = new CartService();
            cartItem.setQuantity(quantity);
            cartService.addToCart(cartItem);

            MessageUtil.showMessage("Success", "Item added to cart successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Error", e.getMessage());
            goBack();
        }
    }

    @Override
    public void setData(Item data) {
        cartItem = new CartItem(data.getId(), data.getName(), SessionManager.getUser().getId(),
                SessionManager.getUser().getName(), data.getPrice(), 0, data.getQuantity());

        updateFields();
    };

    public void updateFields() {
        itemNameField.setText(cartItem.getItemName());
        priceField.setText(String.valueOf(cartItem.getPrice()));
        quantityInStoreField.setText(String.valueOf(cartItem.getQuantityInStore()));
    }

    public void goBack() {
        SceneManager.goBack();
    }

    public void calculateTotalPrice() {
        totalPriceField.setText(String.valueOf(cartItem.getPrice() * Integer.parseInt(quantityField.getText())));
    }
}
