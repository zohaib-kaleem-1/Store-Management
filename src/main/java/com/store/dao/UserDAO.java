package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.db.Database;
import com.store.model.User;

public class UserDAO {
    public boolean addUser(User user) throws Exception {
        String sql = "INSERT INTO users (name, email, contact, username, password, role) VALUES (?,?,?,?,?,?);";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());

            int rc = stmt.executeUpdate();

            return rc < 1 ? false : true;
        }
    }

    public boolean removeUser(String username, String role) throws Exception {
        String sql = "DELETE FROM users WHERE username = ? and role = ?;";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, role);
            int rc = stmt.executeUpdate();

            return rc < 1 ? false : true;
        }
    }

    public boolean updateUser(User user) throws Exception {
        String sql = "UPDATE users SET name = ?, email = ?, contact = ?, password = ? WHERE userid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getId());

            int rc = stmt.executeUpdate();

            return rc < 1 ? false : true;
        }
    }

    public List<User> listUser(String role) {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE role = ?;";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new User(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("contact"), rs.getString("username"), rs.getString("password"),
                        rs.getString("role")));
            }

        } catch (Exception e) {

        }
        return list;
    }

    public User getUserByUsername(String username, String role) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? and role = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, role);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return new User(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("contact"), rs.getString("username"), rs.getString("password"),
                        rs.getString("role"));
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean verifyLogin(String username, String password, String role) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? and password = ? and role = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}