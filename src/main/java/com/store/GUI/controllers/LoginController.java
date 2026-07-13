package com.store.GUI.controllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private Label error;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("admin", "customer");
        error.setText("");

        submitButton.setOnAction(event -> loginVerify());
        usernameField.setOnAction(event -> loginVerify());
        passwordField.setOnAction(event -> loginVerify());
    }

    public void loginVerify() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

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

        if (userService.verifyLogin(username, password, role.toLowerCase())) {
            SessionManager.logUser(userService.getUserByUsername(username, role));
            SceneManager.goToDashboard();
        } else {
            MessageUtil.showError("Login", "Could not verify credentials");
        }

    }
}