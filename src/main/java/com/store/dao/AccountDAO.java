package com.store.dao;

import com.store.HelperFunction.Helper;
import com.store.db.Database;

import java.sql.*;

public class AccountDAO {
    public int getAccountBalance() {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM store;";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return 0;
        } finally {
            Database.closeConnection(conn);
        }

        return 0;
    }

    public boolean updateAccountBalance(int newAmount) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            String sql = "UPDATE  store SET balance = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, newAmount);

            int rc = stmt.executeUpdate();

            Database.closeConnection(conn);
            if (rc < 1) {
                return false;
            }

            return true;
        } catch (Exception e) {
            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }
}
