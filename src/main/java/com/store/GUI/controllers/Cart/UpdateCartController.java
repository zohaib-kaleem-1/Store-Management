package com.store.GUI.controllers.Cart;

import java.sql.Connection;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.db.Database;
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
            if (quantity < 1)
                throw new Exception("Quantity must be at least 1");

            if (SessionManager.getUser().getRole().toLowerCase() == "customer")
                if (cartItem.getQuantityInStore() < quantity)
                    throw new Exception("Quantity must be less than available quantity.");

            cartItem.setQuantity(quantity);
            CartService cartService = new CartService();
            cartItem.setQuantity(quantity);
            cartService.updateCart(conn, cartItem);

            MessageUtil.showMessage("Success", "Cart Updated successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Error", e.getMessage());
        }
    }

    @Override
    public void setData(CartItem data) {
        cartItem = data;
        updateFields();
    };

    public void updateFields() {
        itemNameField.setText(cartItem.getItemName());
        priceField.setText(String.valueOf(cartItem.getPrice()));
        quantityInStoreField.setText(String.valueOf(cartItem.getQuantityInStore()));
    }

    public void goBack() {
        SceneManager.switchScene("/com/store/views/cart/cartview.fxml", "My Cart");
    }

    public void calculateTotalPrice() {
        totalPriceField.setText(String.valueOf(cartItem.getPrice() * Integer.parseInt(quantityField.getText())));
    }

    @FXML
    public void delete() {
        CartService cartService = new CartService();
        try (Connection conn = Database.getConnection()) {
            cartService.removeCart(conn, cartItem.getId());
            MessageUtil.showMessage("Remove Cart Item", "Item removed successfully from cart.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Update Cart", e.getMessage());
        }
    }
}
