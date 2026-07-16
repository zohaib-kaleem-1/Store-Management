package com.store.Util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

/**
 * 
 * MessageUtil
 * 
 * For displaying short info messages and error messages
 */
public class MessageUtil {
    /**
     * Showing short descrition messages and reports to user
     * 
     * @param title   Title of window
     * @param message Message to display in window
     */
    public static void showMessage(String title, String message) {
        Platform.runLater(() -> {
            // Message type
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initStyle(StageStyle.DECORATED);
            alert.showAndWait();
        });
    }

    /**
     * Showing error report to user
     * 
     * @param title   Title of window
     * @param message Message to display in window
     */
    public static void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initStyle(StageStyle.DECORATED);
            alert.showAndWait();
        });
    }

}
