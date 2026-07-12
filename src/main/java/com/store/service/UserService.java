package com.store.service;

import java.sql.Connection;
import java.util.List;

import com.store.dao.UserDAO;
import com.store.model.User;

public class UserService {
    UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean verifyLogin(String username, String password, String role) {
        return userDAO.verifyLogin(username, password, role);
    }

    public User getUserByUsername(String username, String role) {
        return userDAO.getUserByUsername(username, role);
    }

    public void addUser(Connection conn, String name, String email, String contact, String username, String password,
            String role) throws Exception {
        if (userDAO.addUser(conn, new User(name, email, contact, username, password, role))) {
        } else {
        }
    }

    public void addUser(Connection conn, User u) throws Exception {
        if (userDAO.addUser(conn, u)) {
        } else {
        }
    }

    public void removeUser(Connection conn, String username, String role) throws Exception {
        if (userDAO.removeUser(conn, username, role)) {
        } else {
        }
    }

    public boolean findUserByUsername(String username, String role) {
        if (userDAO.getUserByUsername(username, role) != null)
            return true;
        else
            return false;
    }

    public void updateUser(Connection conn, User a) throws Exception {
        if (userDAO.updateUser(conn, a)) {
        } else {
        }
    }

    public void getAllUserByRole(String role) {
        List<User> userList = userDAO.listUser(role);

        if (userList.isEmpty()) {
            return;
        }
    }

}
