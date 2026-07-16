package com.store.GUI.controllers.AdminControllers.ManageAdmin;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddAdminController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;

    @FXML
    public void goBack() {
        SceneManager.goBack();
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
            ValidationUtil.validateUsername(username, "admin");
            ValidationUtil.validatePassword(password);
            ValidationUtil.validateContact(contact);
            ValidationUtil.validateMail(email);

            UserService userService = new UserService();
            userService.addUser(new User(name, email, contact, username, password, "admin"));

            MessageUtil.showMessage("Add Admin", "Admin Added Successfully.");
            SceneManager.switchScene("/com/store/views/adminviews/manageadmin/manageadminview.fxml", "Manage Admin");
        } catch (Exception e) {
            MessageUtil.showError("Add Admin", e.getMessage());
            return;
        }
    }
}