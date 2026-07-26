package com.store.Util;

import java.io.IOException;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;
    private static Stack<SceneHistoryNode> history;

    public static void setStage(Stage stage) {
        primaryStage = stage;
        history = new Stack<>();
    }

    public static void switchScene(String fxmlPath, String title) {
        try {
            loadScene(fxmlPath, title);
            history.push(new SceneHistoryNode(fxmlPath, title));
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> void switchScene(String fxmlPath, String title, T data) {
        try {
            loadScene(fxmlPath, title, data);
            history.push(new SceneHistoryNode(fxmlPath, title, data));
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
        }
    }

    private static void loadScene(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static <T> void loadScene(String fxmlPath, String title, T data) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
        Parent root = loader.load();

        Object controller = loader.getController();
        if (controller instanceof DataReceiver) {
            ((DataReceiver<T>) controller).setData(data);
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void goBack() {
        try {
            if (history == null || history.isEmpty()) {
                throw new Exception("No previous scene to go back to");
            }

            history.pop();

            if (history == null || history.isEmpty()) {
                throw new Exception("No previous scene to go back to");
            }

            SceneHistoryNode previousNode = history.peek();
            if (previousNode.getData() == null) {
                loadScene(previousNode.getFxmlPath(), previousNode.getTitle());
            } else {
                loadScene(previousNode.getFxmlPath(), previousNode.getTitle(), previousNode.getData());
            }
        } catch (Exception e) {
            MessageUtil.showError("Scene Manager", e.getMessage());
        }
    }

    public static void logOut() {
        SessionManager.clear();
        history.clear();
        switchScene("/com/store/views/loginview.fxml", "Login");
    }

    public static void goToDashboard() {
        String role = SessionManager.getUser().getRole();
        switchScene("/com/store/views/" + role + "views/dashboardview.fxml", (role + " Menu").toUpperCase());
    }

    public interface DataReceiver<T> {
        void setData(T data);
    }
}

class SceneHistoryNode {
    private String fxmlPath;
    private String title;
    private Object data;

    public SceneHistoryNode(String path, String title) {
        this.fxmlPath = path;
        this.title = title;
        this.data = null;
    }

    public SceneHistoryNode(String path, String title, Object data) {
        this.fxmlPath = path;
        this.title = title;
        this.data = data;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}