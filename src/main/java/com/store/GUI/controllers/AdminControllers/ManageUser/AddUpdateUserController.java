package com.store.GUI.controllers.AdminControllers.ManageUser;

import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;

public class AddUpdateUserController implements SceneManager.DataReceiver<User> {
    private String mode;
    private String role;
    private User currentUser;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label label;

    @FXML
    private Button deleteButton;

    @FXML
    private void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void delete() {
        try {
            if (new UserService().removeUser(currentUser.getUsername(), currentUser.getRole())) {
                MessageUtil.showMessage("User Manager", role.toUpperCase() + " removed successfully.");
                goBack();
            } else {
                MessageUtil.showMessage("User Manager", "Error removing" + role.toUpperCase());
            }
        } catch (Exception e) {
            MessageUtil.showError("User Manager", e.getMessage());
        }
    }

    @FXML
    public void save() {
        try {
            String username;
            String password;

            String name = nameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();

            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Contact: " + contact);

            ValidationUtil.validateName(name);
            ValidationUtil.validateMail(email);
            ValidationUtil.validateContact(contact);

            System.out.println("save callled");
            if (mode.matches("add")) {
                username = usernameField.getText();
                ValidationUtil.validateUsername(username, role);

                password = passwordField.getText();
                ValidationUtil.validatePassword(password);

                if (new UserService().addUser(new User(name, email, contact, username, password, role.toLowerCase()))) {
                    MessageUtil.showMessage("User Manager", role + " added successfully.");
                    goBack();
                } else
                    MessageUtil.showError("User Manager", "Error adding " + role);
            } else {
                currentUser.setContact(contact);
                currentUser.setName(name);
                currentUser.setEmail(email);

                if (new UserService().updateUser(currentUser)) {
                    MessageUtil.showMessage("User Manager", role + " updated successfully.");
                    goBack();
                } else
                    MessageUtil.showError("User Manager", "Error updating " + role);
            }

        } catch (Exception e) {
            MessageUtil.showError("User Manager", e.getMessage());
        }
    }

    @Override
    public void setData(User data) {
        role = data.getRole();
        currentUser = data;

        if (data.getName() == "") {
            label.setText("Add " + role.toUpperCase());
            mode = "add";
            deleteButton.setVisible(false);
        } else {
            label.setText("Update " + role.toLowerCase());
            mode = "update";
            updateField();
        }
    }

    public void updateField() {
        nameField.setText(currentUser.getName());
        contactField.setText(currentUser.getContact());
        emailField.setText(currentUser.getEmail());
        usernameField.setText(currentUser.getUsername());

        passwordField.setText("**********");

        usernameField.setDisable(true);
        passwordField.setDisable(true);

        deleteButton.setVisible(true);
    }
}
