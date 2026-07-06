package com.store.service;

import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.dao.AdminDAO;
import com.store.model.Admin;

public class AdminService {
    private AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    public void addAdmin(String username, String password) {
        if (adminDAO.addAdmin(new Admin(username, password))) {
            Helper.printColored("Admin Added Successfully.", "green");
        } else {
            Helper.printColored("Error Creating new admin", "red");
        }
    }

    public void removeAdmin(String username) {
        if (adminDAO.removeAdmin(username)) {
            Helper.printColored("Admin Removed Successfully.", "green");
        } else {
            Helper.printColored("Error Removing Admin", "red");
        }
    }

    public boolean findAdminByUsername(String username) {
        if (adminDAO.getAdminByUsername(username) != null)
            return true;
        else
            return false;
    }

    public Admin getAdminByUsername(String Username) {
        return adminDAO.getAdminByUsername(Username);
    }

    public void updateAdmin(Admin a) {
        if (adminDAO.updateAdmin(a)) {
            Helper.printColored("Admin Updated Successfully.", "green");
        } else {
            Helper.printColored("Error Updating Admin", "red");
        }
    }

    public void displayAllAdmin() {
        List<Admin> admins = adminDAO.listAdmin();

        if (admins.isEmpty()) {
            Helper.printColored("NO RECORD FOUND.", "red");
            return;
        }

        Helper.printList(admins);
    }

    public boolean verifyAdminLogin(String username, String password) {
        return adminDAO.verifyAdminLogin(username, password);
    }
}