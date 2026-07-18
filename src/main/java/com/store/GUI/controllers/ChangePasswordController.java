package com.store.GUI.controllers;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.Util.ValidationUtil;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {
    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void changePassword() {
        try {
            if (validatePassword()) {
                new UserService().changePassword(newPasswordField.getText(), SessionManager.getUser().getId());
                MessageUtil.showMessage("Password Changer", "Password Changed Successfully.");
                goBack();
            }

        } catch (Exception e) {
            MessageUtil.showError("Password Changer", e.getMessage());
        }
    }

    @FXML
    public boolean validatePassword() {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        try {
            if (!new UserService().verifyLogin(SessionManager.getUser().getUsername(), currentPassword,
                    SessionManager.getUser().getRole())) {
                MessageUtil.showError("Password Checker", "Current Password is not correct.");
                return false;
            }

            else if (!ValidationUtil.validatePassword(newPasswordField.getText()))
                return false;

            else if (!newPassword.matches(confirmNewPassword)) {
                MessageUtil.showError("Password Checker", "Password Do Not Matches");
                return false;
            }

            return true;
        } catch (Exception e) {
            MessageUtil.showError("Password Checker", e.getMessage());
            return false;
        }

    }
}