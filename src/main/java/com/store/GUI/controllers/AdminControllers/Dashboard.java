package com.store.GUI.controllers.AdminControllers;

import com.store.Util.SceneManager;

import javafx.fxml.FXML;

public class Dashboard {

    @FXML
    public void goToManageAdminView() {
        SceneManager.switchScene("/com/store/views/adminviews/manageadminview.fxml", "Manage Admins");
    }

    @FXML
    public void goToManageCustomerView() {
        SceneManager.switchScene("/com/store/views/adminviews/managecustomerview.fxml", "Manage Customer");

    }

    @FXML
    public void goToManageItemView() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitemview.fxml", "Manage Item");

    }

    @FXML
    public void goToManageAccountView() {
        SceneManager.switchScene("/com/store/views/manageaccountview.fxml", "Manage Account");

    }

    @FXML
    public void logOut() {
        SceneManager.goToLogin();
    }
}