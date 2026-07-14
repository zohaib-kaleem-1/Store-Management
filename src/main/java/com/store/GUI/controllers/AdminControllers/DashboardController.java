package com.store.GUI.controllers.AdminControllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.service.BalanceService;

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
        SceneManager.goToLogin();
    }

    @FXML
    public void goToOrderView() {
        SceneManager.switchScene("/com/store/views/adminviews/orderview.fxml", "Order");
    }

    @FXML
    public void viewBalance() {
        try (BalanceService balanceService = new BalanceService()) {
            MessageUtil.showMessage("Balance", "Current Balance: " + balanceService.getBalance());
        }
    }
}