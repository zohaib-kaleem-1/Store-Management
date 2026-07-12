package com.store.GUI.controllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.db.Database;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ManageAccount {
    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField emailField;

    @FXML
    public void goToDashboard() {
        SceneManager.goToDashboard();
    }

    @FXML
    public void save() {
        String password = passwordField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();

        try {
            ValidationUtil.validateName(name);
            ValidationUtil.validateContact(contact);
            ValidationUtil.validateMail(email);
            ValidationUtil.validatePassword(password);

            SessionManager.getUser().setName(name);
            SessionManager.getUser().setPassword(password);
            SessionManager.getUser().setContact(contact);
            SessionManager.getUser().setEmail(email);

            UserService userService = new UserService();
            userService.updateUser(Database.getConnection(), SessionManager.getUser());

            MessageUtil.showMessage("Store", "User updated successfully.");
        } catch (Exception e) {
            MessageUtil.showError("Invalid Input", e.getMessage());
            return;
        }
    }

    @FXML
    public void initialize() {
        User user = SessionManager.getUser();

        nameField.setText(user.getName());
        usernameField.setText(user.getUsername());
        usernameField.setDisable(true);
        passwordField.setText(user.getPassword());
        emailField.setText(user.getEmail());
        contactField.setText(user.getContact());

    }
}
