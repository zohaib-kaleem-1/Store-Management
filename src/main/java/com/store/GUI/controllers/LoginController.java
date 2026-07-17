package com.store.GUI.controllers;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    /**
     * initialize the controllers
     * add on action listener
     */
    public void initialize() {
        roleComboBox.getItems().addAll("Admin", "Customer");
        roleComboBox.setValue("Admin");

        submitButton.setOnAction(event -> loginVerify());
        usernameField.setOnAction(event -> loginVerify());
        passwordField.setOnAction(event -> loginVerify());
    }

    /**
     * Validate user login credentails
     */
    public void loginVerify() {
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
}