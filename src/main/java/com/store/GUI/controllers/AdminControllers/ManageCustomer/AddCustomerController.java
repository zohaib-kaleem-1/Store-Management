package com.store.GUI.controllers.AdminControllers.ManageCustomer;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;

    @FXML
    public void goBack() {
        SceneManager.switchScene("/com/store/views/adminviews/managecustomer/managecustomerview.fxml", "Manage Admin");
    }

    @FXML
    public void save() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();

        try {
            ValidationUtil.validateName(name);
            ValidationUtil.validateUsername(username, "customer");
            ValidationUtil.validatePassword(password);
            ValidationUtil.validateContact(contact);
            ValidationUtil.validateMail(email);

            UserService userService = new UserService();
            userService.addUser(new User(name, email, contact, username, password, "customer"));

            MessageUtil.showMessage("Add Admin", "Admin Added Successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Add Admin", e.getMessage());
            return;
        }
    }
}