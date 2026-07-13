package com.store.service;

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

    public void addUser(String name, String email, String contact, String username, String password,
            String role) throws Exception {
        if (userDAO.addUser(new User(name, email, contact, username, password, role))) {
        } else {
        }
    }

    public void addUser(User u) throws Exception {
        if (userDAO.addUser(u)) {
        } else {
        }
    }

    public void removeUser(String username, String role) throws Exception {
        if (userDAO.removeUser(username, role)) {
        } else {
        }
    }

    public boolean findUserByUsername(String username, String role) {
        if (userDAO.getUserByUsername(username, role) != null)
            return true;
        else
            return false;
    }

    public void updateUser(User a) throws Exception {
        if (userDAO.updateUser(a)) {
        } else {
        }
    }

    public List<User> getAllUserByRole(String role) {
        return userDAO.listUser(role);
    }

}
