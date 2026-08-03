package com.store;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            primaryStage.setResizable(false);

            // setup stage for showing graphs
            SceneManager.setStage(primaryStage);

            // switch to login view
            SceneManager.logOut();
        } catch (Exception e) {
            MessageUtil.showError("Scene Laoder", e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}