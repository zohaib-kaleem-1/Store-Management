package com.store;

import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.User;
import com.store.service.UserService;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // setup stage for showing graphs
            SceneManager.setStage(primaryStage);

            // switch to login view
            SceneManager.logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
