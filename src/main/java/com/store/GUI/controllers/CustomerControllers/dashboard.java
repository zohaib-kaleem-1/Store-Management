package com.store.GUI.controllers.CustomerControllers;

import com.store.Util.SceneManager;
import com.store.Util.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class dashboard {
    @FXML
    public Label customerNameLabel;

    @FXML
    public void switchToBuyItem() {
        SceneManager.switchScene("/com/store/views/customerview/buyitemview.fxml", "Buy Items");
    }

    @FXML
    public void switchToManageAccount() {
        SceneManager.switchScene("com/store/views/customerview/manageaccountview.fxml", "Manage Account");
    }

    @FXML
    public void switchToViewCart() {
        SceneManager.switchScene("/com/store/views/customerviews/cartview.fxml", "Cart");
    }

    @FXML
    public void logOut() {
        SceneManager.goToLogin();

    }

    @FXML
    public void initialize() {
        customerNameLabel.setText("Welcome " + SessionManager.getUser().getName().toUpperCase());
    }
}
