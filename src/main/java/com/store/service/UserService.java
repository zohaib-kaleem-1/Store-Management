package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.UserDAO;
import com.store.model.User;

public class UserService {
    UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean verifyLogin(String username, String password, String role) throws SQLException {
        return userDAO.verifyLogin(username, password, role);
    }

    public User getUserByUsername(String username, String role) throws SQLException {
        return userDAO.getUserByUsername(username, role);
    }

    public boolean addUser(User u) throws SQLException {
        return userDAO.addUser(u);
    }

    public boolean removeUser(String username, String role) throws SQLException {
        return userDAO.removeUser(username, role);
    }

    // SEE if username exists
    public boolean findUserByUsername(String username, String role) throws SQLException {
        if (userDAO.getUserByUsername(username, role) != null)
            return true;
        else
            return false;
    }

    public boolean updateUser(User a) throws SQLException {
        return userDAO.updateUser(a);
    }

    public List<User> getAllUserByRole(String username, String role, int limit, int pageIndex) throws SQLException {
        return userDAO.listUser(username, role, limit, pageIndex);
    }

    public int getRowCount(String role) throws SQLException {
        return userDAO.getRowCount(role);
    }
}
