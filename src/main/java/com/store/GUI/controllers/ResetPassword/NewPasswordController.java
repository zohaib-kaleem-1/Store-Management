package com.store.GUI.controllers.ResetPassword;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class NewPasswordController implements SceneManager.DataReceiver<Integer> {
    private Integer userId;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    public void changePassword() {
        try {
            String newPassword = newPasswordField.getText();
            String confirmNewPassword = confirmNewPasswordField.getText();

            if (!newPassword.matches(confirmNewPassword))
                throw new Exception("Password do not matches");

            ValidationUtil.validatePassword(newPassword);

            if (new UserService().changePassword(newPassword, userId)) {
                MessageUtil.showMessage("Reset Password Manager", "Password updated successfully.");
                SceneManager.logOut();
            } else
                throw new Exception("Could not change password");
        } catch (Exception e) {
            MessageUtil.showError("Reset Password Manager", e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @Override
    public void setData(Integer data) {
        userId = data;
    }
}
