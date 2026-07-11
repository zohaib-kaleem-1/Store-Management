package com.store;

import com.store.Util.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            SceneManager.setStage(primaryStage);

            SceneManager.switchScene("/com/store/views/login.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}