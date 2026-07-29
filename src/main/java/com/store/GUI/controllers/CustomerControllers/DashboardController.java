package com.store.GUI.controllers.CustomerControllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.OrderService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label numberOfCartItemsLabel;

    @FXML
    private Label totalSpentLabel;

    @FXML
    private Label numberOfActiveOrdersLabel;

    @FXML
    public void initialize() {
        String firstName = SessionManager.getUser().getName().trim().split(" ")[0];
        welcomeLabel.setText("Welcome " + firstName + "!");

        try {
            OrderService orderService = new OrderService();
            int currentUserId = SessionManager.getUser().getId();

            balanceLabel.setText(String.valueOf(new CustomerService().getBalance(currentUserId)));
            numberOfCartItemsLabel
                    .setText(String.valueOf(new CartService().getRowCount(currentUserId, "")));

            totalSpentLabel.setText(String.valueOf(orderService.getTotalSpent(currentUserId)));
            numberOfActiveOrdersLabel.setText(String.valueOf(orderService.getRowCountForUser(currentUserId, "", "")
                    - orderService.getRowCountForUser(currentUserId, "delivered", "")));

        } catch (Exception e) {
            MessageUtil.showError("Cart Manager", e.getMessage());
        }
    }

    @FXML
    public void goToBuyItem() {
        SceneManager.switchScene("/com/store/views/customerviews/buyitem/buyitemview.fxml", "Buy Items");
    }

    @FXML
    public void goToViewCart() {
        SceneManager.switchScene("/com/store/views/customerviews/cart/cartview.fxml", "My Cart");
    }

    @FXML
    public void goToChangePassword() {
        SceneManager.switchScene("/com/store/views/changepasswordview.fxml", "Change Password");
    }

    @FXML
    public void goToViewOrderHistory() {
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
