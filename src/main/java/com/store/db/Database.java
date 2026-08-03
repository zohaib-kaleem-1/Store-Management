package com.store.db;

import java.sql.*;

import com.store.Util.MessageUtil;

/**
 * 
 * Database
 * 
 * Connects to database using credentials
 */
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/store";
    private static final String USER = "postgres";
    private static final String PASS = "zohaib";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                MessageUtil.showError("Database Loader", e.getMessage());
            }
        }
    }
}