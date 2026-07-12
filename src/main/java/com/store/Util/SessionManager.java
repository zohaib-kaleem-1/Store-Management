package com.store.Util;

import com.store.model.User;

public class SessionManager {
    private static User user;

    public static void logUser(User user) {
        SessionManager.user = user;
    }

    public static void clear() {
        user = null;
    }

    public static User getUser() {
        return user;
    }
}
