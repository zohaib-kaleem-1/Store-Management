package com.store.GUI.controllers.CustomerControllers;

import com.store.Util.SceneManager;

import javafx.fxml.FXML;

public class DashboardController {
    @FXML
    public void goToBuyItem() {
        SceneManager.switchScene("/com/store/views/customerviews/buyitem/buyitemview.fxml", "Buy Items");
    }

    @FXML
    public void goToViewCart() {
        SceneManager.switchScene("/com/store/views/customerviews/cartview.fxml", "My Cart");
    }

    @FXML
    public void goToChangePassword() {
        SceneManager.switchScene("/com/store/views/changepasswordview.fxml", "Change Password");
    }

    @FXML
    public void goToViewOrder() {
        SceneManager.switchScene("/com/store/views/customerviews/orderview.fxml", "View Order");
    }

    @FXML
    public void goToManageAccount() {
        SceneManager.switchScene("/com/store/views/manageaccountview.fxml", "Manage Account");
    }

    @FXML
    public void logOut() {
        SceneManager.logOut();
    }
}
