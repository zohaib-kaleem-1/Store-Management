package com.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.store.db.Database;

public class CustomerDAO {
    public Double getBalance(int userid) throws SQLException {
        String sql = """
                    SELECT balance FROM customerbalance WHERE USERID = ?;
                """;
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

            sql = """
                    INSERT INTO customerBalance (userid, balance) VALUES (?, 0.00);
                    """;
            try (PreparedStatement stmt1 = conn.prepareStatement(sql)) {

                stmt1.execute();
            }

            return 0.0;
        }
    }
}
