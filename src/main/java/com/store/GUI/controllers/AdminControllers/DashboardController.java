package com.store.GUI.controllers.AdminControllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.service.StoreService;

import javafx.fxml.FXML;

public class DashboardController {

    @FXML
    public void goToManageAdminView() {
        SceneManager.switchScene("/com/store/views/adminviews/manageadmin/manageadminview.fxml", "Manage Admins");
    }

    @FXML
    public void goToManageCustomerView() {
        SceneManager.switchScene("/com/store/views/adminviews/managecustomer/managecustomerview.fxml",
                "Manage Customer");

    }

    @FXML
    public void goToBuyItemView() {
        SceneManager.switchScene("/com/store/views/buyitem/buyitemview.fxml", "Buy Item");
    }

    @FXML
    public void goToManageItemView() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/manageitemview.fxml", "Manage Item");

    }

    @FXML
    public void goToManageAccountView() {
        SceneManager.switchScene("/com/store/views/manageaccountview.fxml", "Manage Account");

    }

    @FXML
    public void logOut() {
        SessionManager.clear();
        SceneManager.goBack();
    }

    @FXML
    public void goToOrderView() {
        SceneManager.switchScene("/com/store/views/adminviews/orderview.fxml", "Order");
    }

    @FXML
    public void viewBalance() {
        try {
            StoreService storeService = new StoreService();
            MessageUtil.showMessage("Balance", "Current Balance: " + storeService.getBalance());
        } catch (Exception e) {
            MessageUtil.showMessage("Error", e.getMessage());
        }
    }
}