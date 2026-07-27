package com.store.GUI.controllers;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * LoginController
 * 
 * Controller for login page
 * Handles username password input and verification
 */
public class LoginController {
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink registerUserLink;

    @FXML
    /**
     * initialize the controllers
     * add on action listener
     */
    public void initialize() {
        roleComboBox.getItems().addAll("Admin", "Customer");
        roleComboBox.setValue("Customer");

        roleComboBox.valueProperty().addListener(event -> {
            updateCreateAccountButtonView();
        });

        updateCreateAccountButtonView();
    }

    private void updateCreateAccountButtonView() {
        if (roleComboBox.getValue().toLowerCase().matches("admin"))
            registerUserLink.setVisible(false);
        else
            registerUserLink.setVisible(true);
    }

    /**
     * Validate user login credentails
     */
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        // Checks if any credentail is empty
        if (role == null || role.trim().isEmpty()) {
            MessageUtil.showError("Invalid Choice", "Please choose a role first");
            return;
        }
        if (username == null || username.trim().isEmpty()) {
            MessageUtil.showError("Invalid Choice", "Please enter a valid username");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            MessageUtil.showError("Invalid Choice", "Please enter a valid password");
            return;
        }

        UserService userService = new UserService();

        try {
            // Verify username and password from database
            if (userService.verifyLogin(username, password, role.toLowerCase())) {

                MessageUtil.showMessage("Password Authentication", "User logged in successfully.");
                // Store user data in session manager for other screen
                SessionManager.logUser(userService.getUserByUsername(username, role.toLowerCase()));

                // Go to dashboard by role
                SceneManager.goToDashboard();
            } else
                MessageUtil.showError("Password Authentication", "Incorrect Credentails");
        } catch (SQLException e) {
            MessageUtil.showError("Password Verification", e.getMessage());
        }
    }

    @FXML
    public void resetPassword() {
        SceneManager.switchScene("/com/store/views/resetpassword/verifycredentialview.fxml", "Reset Password");
    }

    @FXML
    public void registerUser() {
        SceneManager.switchScene("/com/store/views/adminviews/manageuser/addupdateuserview.fxml", "Register Customer",
                new User("", "", "", "", "", "customer"));

    }
}