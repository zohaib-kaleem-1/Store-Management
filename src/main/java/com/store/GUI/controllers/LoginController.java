package com.store.GUI.controllers;

import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.Admin;
import com.store.model.Customer;
import com.store.model.User;
import com.store.service.AdminService;
import com.store.service.CustomerService;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    public void initialize() {
        roleComboBox.getItems().addAll("admin", "customer");

        submitButton.setOnAction(event -> loginVerify());
        usernameField.setOnAction(event -> loginVerify());
        passwordField.setOnAction(event -> loginVerify());
    }

    public void loginVerify() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (role == null || role.trim().isEmpty()) {
            // TODO add error show
        }
        if (username == null || username.trim().isEmpty()) {
            // TODO add error show
        }
        if (password == null || password.trim().isEmpty()) {
            // TODO add error show
        }

        UserService userService = new UserService();

        if(userService.verifyLogin(username, password, role.toLowerCase())){
            SessionManager.logUser(, , );
        }

    }
}