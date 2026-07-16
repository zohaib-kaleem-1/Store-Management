package com.store.GUI.controllers.AdminControllers.ManageAdmin;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UpdateAdminController implements SceneManager.DataReceiver<User> {
    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField emailField;

    private User userToUpdate;

    @FXML
    public void goBack() {
        SceneManager.goBack();
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

            UserService userService = new UserService();
            userService.updateUser(userToUpdate);

            MessageUtil.showMessage("Store", "User updated successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Invalid Input", e.getMessage());
            return;
        }
    }

    @FXML
    public void updateFields() {
        nameField.setText(userToUpdate.getName());
        usernameField.setText(userToUpdate.getUsername());
        usernameField.setDisable(true);
        passwordField.setText(userToUpdate.getPassword());
        emailField.setText(userToUpdate.getEmail());
        contactField.setText(userToUpdate.getContact());
    }

    @FXML
    public void delete() {
        UserService userService = new UserService();
        try {
            userService.removeUser(userToUpdate.getUsername(), "admin");
            MessageUtil.showMessage("Delete User", "Admin Deleted Successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Update Admin", e.getMessage());
        }
    }

    @Override
    public void setData(User data) {
        this.userToUpdate = data;
        userToUpdate.setRole("admin");
        updateFields();
    }
}
