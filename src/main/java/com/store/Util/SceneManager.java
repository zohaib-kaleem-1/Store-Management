package com.store.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * SceneManager
 * 
 * Scene manager util is to handle switching windows and stage controller
 */
public class SceneManager {
    private static Stage primaryStage;

    private static List<SceneHistoryNode> node;

    /**
     * Setting up stage for later window switching
     * 
     * @param stage the stage on which we will setup scenes
     */
    public static void setStage(Stage stage) {
        primaryStage = stage;
        node = new ArrayList<SceneHistoryNode>();
    }

    /**
     * Switching scene between function
     * add current scene to history
     * 
     * @param fxmlPath
     * @param title
     */
    public static void switchScene(String fxmlPath, String title) {
        try {
            // Load Scene
            loadScene(fxmlPath, title);

            // Add to scene history
            node.add(new SceneHistoryNode(fxmlPath, title));
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
        }
    }

    /**
     * Switching scenes between windows
     * 
     * @param fxmlPath the path to view file
     * @param title    title of the window
     */
    public static void loadScene(String fxmlPath, String title) throws IOException {
        try {
            // Loading the fxml file
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle(title);

            // adding scene to the stage
            primaryStage.setScene(scene);
            primaryStage.show();
        } finally {

        }
    }

    /**
     * Going back to last scene
     */
    public static void goBack() {
        try {
            if (node == null || node.isEmpty())
                throw new Exception("Nowhere to go ");

            // remove current scene from node history
            node.removeLast();

            if (node == null || node.isEmpty())
                throw new Exception("Nowhere to go ");
            else
                loadScene(node.getLast().getFxmlPath(), node.getLast().getTitle());

            // loading previous scene
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
        }
    }

    // Go to login menu
    public static void goToLogin() {
        switchScene("/com/store/views/loginview.fxml", "Login");
    }

    // Go to dashboard according to current user role
    public static void goToDashboard() {
        String role = SessionManager.getUser().getRole();
        SceneManager.switchScene("/com/store/views/" + role + "views/dashboardview.fxml",
                (role + " Menu").toUpperCase());
    }

    /**
     * Switching scene with transmitting data using template
     * 
     * @param <T>      The template of which the data will be transferred
     * @param fxmlPath The path to the file
     * @param title    Title of the window
     * @param data     The data
     */
    public static <T> void switchScene(String fxmlPath, String title, T data) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            // If the controller implements a DataReceiver interface, pass the data
            Object controller = loader.getController();
            if (controller instanceof DataReceiver) {
                ((DataReceiver<T>) controller).setData(data);
            }

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.show();

            node.add(new SceneHistoryNode(fxmlPath, title));
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
        }
    }

    /**
     * 
     * DataReceiver
     * 
     * Receiver must implement the interface and override this function
     * 
     * @param <T>
     */
    public interface DataReceiver<T> {
        void setData(T data);
    }
}

class SceneHistoryNode {
    private String fxmlPath;
    private String title;

    public SceneHistoryNode(String path, String title) {
        this.fxmlPath = path;
        this.title = title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public String getTitle() {
        return title;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}