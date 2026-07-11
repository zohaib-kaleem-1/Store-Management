package com.store.Util;

public class SessionManager {
    private static String username;
    private static String name;
    private static int id;
    private static String role;

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static void setName(String name) {
        SessionManager.name = name;
    }

    public static void setUsername(String username) {
        SessionManager.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SessionManager.role = role;
    }

    public static void logUser(String name, String username, int id, String role) {
        SessionManager.username = username;
        SessionManager.name = name;
        SessionManager.id = id;
        SessionManager.role = role;
    }

    public static void logUser(String username, int id, String role) {
        SessionManager.username = username;
        SessionManager.id = id;
        SessionManager.role = role;
    }

    public static void clear() {
        username = "";
        name = "";
        id = 0;
        role = "";
    }
}
