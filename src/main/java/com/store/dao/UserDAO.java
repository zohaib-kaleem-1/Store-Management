package com.store.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.store.Util.PasswordAuthUtil;
import com.store.db.Database;
import com.store.model.User;

public class UserDAO {
    /**
     * Add new user to database
     * 
     * @param user
     * @return true if user is added
     * @throws Exception
     */
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, contact, username, password, role) VALUES (?,?,?,?,?,?);";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, PasswordAuthUtil.encoder(user.getPassword()));
            stmt.setString(6, user.getRole());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * Remove user from database
     * 
     * @param username username to delete
     * @param role     role to delete due to UNIQUE(username, role ) constraint
     * @return true if user is deleted false if user not found
     * @throws Exception
     */
    public boolean removeUser(String username, String role) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ? and role = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, role);
            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * update user information
     * 
     * @param user
     * @return true id information is added false if not found
     * @throws Exception
     */

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, contact = ?, password = ? WHERE userid = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getContact());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getId());

            return stmt.executeUpdate() == 1 ? true : false;
        }
    }

    /**
     * List all users in database by role
     * 
     * @param role
     * @return
     */
    public List<User> listUser(String role) throws SQLException {
        List<User> list = new ArrayList<>();

        String sql = "SELECT userid, name, email, contact, username FROM users WHERE role = ?;";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new User(rs.getInt("userid"), rs.getString("name"), rs.getString("email"),
                        rs.getString("contact"), rs.getString("username"), "",
                        role));
            }

        }
        return list;
    }

    /**
     * get user information by username
     * 
     * @param username
     * @param role
     * @return user information or null if user not found
     * @throws Exception
     */
    public User getUserByUsername(String username, String role) throws SQLException {
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
        }
    }

    /**
     * Verify login of user
     * 
     * @param username
     * @param password
     * @param role
     * @return true if login is verified
     * @throws Exception
     */
    public boolean verifyLogin(String username, String password, String role) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ? and role = ?;";

        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, role);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return PasswordAuthUtil.verifyPassword(password, rs.getString("password"));
            }

            return rs.next();
        }
    }
}