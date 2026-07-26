package com.store.GUI.controllers.ResetPassword;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.ValidationUtil;
import com.store.model.User;
import com.store.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class VerifyCredentialsController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Admin", "Customer");
        roleComboBox.setValue("Admin");
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void verifyCredential() {
        try {
            String username = usernameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String role = roleComboBox.getValue().toLowerCase();

            if (role == null || role.isEmpty())
                throw new Exception("No role is selected");

            if (username == null || username.isEmpty())
                throw new Exception("Username cant be null");
            else if (!new UserService().findUserByUsername(username, role)) {
                throw new Exception("Username don't exist");
            }
            ValidationUtil.validateMail(email);
            ValidationUtil.validateContact(contact);

            User temp = new UserService().getUserByUsername(username, role);

            if (temp.getEmail().matches(email) && temp.getContact().matches(contact)) {
                MessageUtil.showMessage("Reset Password", "Credentials verified");
                SceneManager.switchScene("/com/store/views/resetpassword/newpasswordview.fxml", "Reset Password",
                        temp.getId());
            } else
                throw new Exception("Credentials not verified.\nIncorrect Credentials");

        } catch (Exception e) {
            MessageUtil.showError("Reset Password", e.getMessage());
        }
    }
}
