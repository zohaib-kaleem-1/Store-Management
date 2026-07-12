package com.store.Util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToLogin() {
        SessionManager.clear();
        switchScene("/com/store/views/login.fxml", "Login");
    }

    public static void goToDashboard() {
        String role = SessionManager.getUser().getRole();
        SceneManager.switchScene("/com/store/views/" + role + "views/dashboard.fxml",
                (role + " Menu").toUpperCase());
    }

    public static void goToManageAccount() {
        switchScene("/com/store/views/manageaccountview.fxml", "Manage Account");
    }
}