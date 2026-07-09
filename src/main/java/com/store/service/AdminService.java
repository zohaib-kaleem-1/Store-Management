package com.store.service;

import java.util.List;

import com.store.dao.UserDAO;
import com.store.model.Admin;
import com.store.model.User;

public class AdminService {
    private UserDAO userDAO;

    public AdminService() {
        this.userDAO = new UserDAO();
    }

    public void addAdmin(String name, String email, String contact, String username, String password) {
        if (userDAO.addUser(new Admin(name, email, contact, username, password), "admin")) {
        } else {
        }
    }

    public void removeAdmin(String username) {
        if (userDAO.removeUser(username, "admin")) {
        } else {
        }
    }

    public boolean findAdminByUsername(String username) {
        if (userDAO.getUserByUsername(username, "admin") != null)
            return true;
        else
            return false;
    }

    public User getAdminByUsername(String Username) {
        return userDAO.getUserByUsername(Username, "admin");
    }

    public void updateAdmin(Admin a) {
        if (userDAO.updateUser(a)) {
        } else {
        }
    }

    public void displayAllAdmin() {
        List<User> admins = userDAO.listUser("admin");

        if (admins.isEmpty()) {
            return;
        }
    }

    public boolean verifyAdminLogin(String username, String password) {
        return userDAO.verifyLogin(username, password, "admin");
    }
}