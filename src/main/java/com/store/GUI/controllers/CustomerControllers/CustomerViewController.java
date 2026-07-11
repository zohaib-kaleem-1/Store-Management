package com.store.GUI.controllers.CustomerControllers;

import com.store.Util.SceneManager;
import com.store.Util.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomerViewController {
    @FXML
    public Label customerNameLabel;

    @FXML
    public void switchToBuyItem() {
        SceneManager.switchScene("com.store.views.customerview.buyitemview.fxml", "Buy Items");
    }

    @FXML
    public void switchToManageAccount() {
        SceneManager.switchScene("com.store.views.customerview.manageaccountview.fxmls", "Manage Account");
    }

    @FXML
    public void switchToViewCart() {
        SceneManager.switchScene("com.store.views.customerviews.cartview.fxml", "Cart");
    }

    @FXML
    public void logOut() {
        System.out.println("log out");

    }

    @FXML
    public void initialize() {
        customerNameLabel.setText("Welcome " + SessionManager.getUsername().toUpperCase());
    }
}
