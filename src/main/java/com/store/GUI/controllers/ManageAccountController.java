package com.store.GUI.controllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ManageAccountController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField emailField;

    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SessionManager.getUser();

        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        usernameField.setText(currentUser.getUsername());
        usernameField.setDisable(true);
        contactField.setText(currentUser.getContact());
    }

    @FXML
    public void save() {
        try {
            String name = nameField.getText();
            String contact = contactField.getText();
            String email = emailField.getText();

            ValidationUtil.validateName(name);
            ValidationUtil.validateContact(contact);
            ValidationUtil.validateMail(email);

            currentUser.setName(name);
            currentUser.setEmail(email);
            currentUser.setContact(contact);

            new UserService().updateUser(currentUser);

            MessageUtil.showMessage("Update User Manager", "User updated successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Update User Manager", e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }
}
