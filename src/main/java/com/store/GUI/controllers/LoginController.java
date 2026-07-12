package com.store.GUI.controllers;

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
            error.setText("Role can't be empty");
            return;
        }
        if (username == null || username.trim().isEmpty()) {
            error.setText("Username can't be empty");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            error.setText("Password can't be empty");
            return;
        }

        UserService userService = new UserService();

        if (userService.verifyLogin(username, password, role.toLowerCase())) {
            SessionManager.logUser(userService.getUserByUsername(username, role));
            SceneManager.switchScene("/com/store/views/" + role + "views/dashboard.fxml",
                    (role + " Menu").toUpperCase());
        } else {
            error.setText("Could not verify credentials");
        }

    }
}