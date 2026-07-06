package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.db.Database;
import com.store.model.Admin;

public class AdminDAO {
    public boolean addAdmin(Admin admin) {
        Connection conn = null;
        try {
            String sql = "INSERT INTO Admin (username, password) VALUES (?,?);";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());

            int rc = stmt.executeUpdate();

            Database.closeConnection(conn);
            if (rc < 1)
                return false;

            return true;

        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean removeAdmin(String username) {
        Connection conn = null;
        try {
            String sql = "DELETE FROM admin WHERE username = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rc = stmt.executeUpdate();

            Database.closeConnection(conn);
            if (rc < 1)
                return false;

            return true;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(conn);
        }
    }

    public boolean updateAdmin(Admin admin) {
        Connection conn = null;
        try {
            String sql = "UPDATE admin SET username = ?, password = ? WHERE adminid = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setInt(3, admin.getId());

            int rc = stmt.executeUpdate();

            Database.closeConnection(conn);
            if (rc < 1)
                return false;

            return true;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(null);
        }
    }

    public List<Admin> listAdmin() {
        Connection conn = null;
        try {
            List<Admin> adminList = new ArrayList<>();
            String sql = "SELECT * FROM admin;";
            conn = Database.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                adminList.add(new Admin(rs.getInt("adminid"), rs.getString("username"), rs.getString("password")));
            }

            return adminList;
        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(null);
        }
    }

    public Admin getAdminByUsername(String username) {
        Connection conn = null;
        try {
            String sql = "SELECT * FROM admin WHERE username = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return new Admin(rs.getInt("adminid"), rs.getString("username"), rs.getString("password"));

            return null;

        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return null;
        } finally {
            Database.closeConnection(null);
        }
    }

    public boolean verifyAdminLogin(String username, String password) {
        Connection conn = null;
        try {
            String sql = "SELECT * FROM admin WHERE username = ? and password = ?;";

            conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return true;

            return false;

        } catch (Exception e) {

            Helper.printColored("Error: " + e.getMessage(), "red");
            return false;
        } finally {
            Database.closeConnection(null);
        }
    }
}