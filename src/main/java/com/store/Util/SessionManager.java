package com.store.Util;

import com.store.model.User;

/**
 * 
 * SessionManager
 * 
 * Utility to store the information of currently logged user
 * so it can be ascessed by all windows
 * 
 */
public class SessionManager {
    private static User user;

    // Get data of user logging
    public static void logUser(User user) {
        SessionManager.user = user;
    }

    // Clear data of user when logging out
    public static void clear() {
        user = null;
    }

    // Get user data for processing and updating
    public static User getUser() {
        return user;
    }
}
