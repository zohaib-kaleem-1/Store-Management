package com.store.View;

import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.model.*;
import com.store.service.*;

public class AdminView {
    AdminService adminService;
    CustomerService customerService;
    ItemService itemService;
    ItemHistoryService itemHistoryService;
    AccountService accountService;

    Admin currentAdmin;

    AdminView() {
        this.adminService = new AdminService();
        this.customerService = new CustomerService();
        this.itemService = new ItemService();
        this.itemHistoryService = new ItemHistoryService();
        this.accountService = new AccountService();

        this.currentAdmin = login();

        if (currentAdmin != null) {
            AdminMenu();
        }
    }

    public void ManageAdminMenu() {
        while (true) {
            adminService.displayAllAdmin();
            System.out.println("1.Add new admin");
            System.out.println("2.Remove admin");
            System.out.println("3.Update an admin");
            System.out.println("4.Back to Main Menu");
            int choice = Helper.intInput("Enter Your Choice: ", 1, 4);

            switch (choice) {
                case 1:
                    String username = Helper.inputString("Enter username of Admin: ");
                    String password = Helper.inputString("Enter password of Admin: ");

                    adminService.addAdmin(username, password);
                    break;
                case 2:
                    String usernameToDelete = Helper.inputString("Enter username to delete: ");
                    adminService.removeAdmin(usernameToDelete);
                    break;
                case 3:
                    String usernameToUpdate = Helper.inputString("Enter username to update: ");

                    if (adminService.findAdminByUsername(usernameToUpdate)) {
                        System.out.println("1.Change Username");
                        System.out.println("2.Change Password");
                        int ch = Helper.intInput("Enter your choice: ", 1, 2);
                        Admin admin = adminService.getAdminByUsername(usernameToUpdate);
                        if (admin == null) {
                            Helper.printColored("Error raeding admin data", "red");
                        }

                        switch (ch) {
                            case 1:
                                String newUsername = Helper.inputString("Enter new Username: ");
                                admin.setUsername(newUsername);
                                adminService.updateAdmin(admin);
                                break;
                            case 2:
                                String newPassword = Helper.inputString("Enter new password: ");
                                admin.setPassword(newPassword);
                                adminService.updateAdmin(admin);
                                break;
                        }
                    } else {
                        Helper.printColored("Error: USER NOT FOUND", "red");
                    }
                    break;
                case 4:
                    Helper.printColored("Going to main menu....", "blue");
                    return;
            }
        }
    }

    public void ManageCustomerMenu() {
        while (true) {
            System.out.println("1.List All Costumers");
            System.out.println("2.View Customer Detail");
            System.out.println("3.Delete Customer Data");
            System.out.println("4.Update Customer Information");
            System.out.println("5.Back to Main Menu");
            int choice = Helper.intInput("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    customerService.displayAllCustomer();
                    break;
                case 2:
                    String custumerUsername = Helper.inputString("Enter customer username: ");

                    Customer cuss = customerService.getCustomerByUsername(custumerUsername);
                    if (cuss != null) {
                        List<Customer> temp = new ArrayList<>();
                        temp.add(cuss);
                        Helper.printColored("CUSTOMER DETAILS", "blue");
                        Helper.printList(temp);

                        Helper.printColored("CUSTOMER PURCHASE HISTORY", "blue");
                        itemHistoryService.displayItemHistoryByCustomerId(cuss.getId());
                    } else {
                        Helper.printColored("Error: CUSTOMER NOT FOUND.", "red");
                    }

                    break;
                case 3:
                    String usernameToDelete = Helper.inputString("Enter customer username to DELETE: ");
                    customerService.removeCustomer(usernameToDelete);
                    break;
                case 4:
                    String usernameToUpdate = Helper.inputString("Enter customer username to UPDATE: ");

                    if (customerService.findCustomerByUsername(usernameToUpdate)) {

                        System.out.println("1.Change Name");
                        System.out.println("2.Change Password");
                        System.out.println("3.Change Email");
                        System.out.println("4.Change ContactNo");

                        int ch = Helper.intInput("Enter Your Choice: ", 1, 4);
                        Customer customerToUpdate = customerService.getCustomerByUsername(usernameToUpdate);
                        if (customerToUpdate == null) {
                            Helper.printColored("Error reading customer data", "red");
                            break;
                        }

                        switch (ch) {
                            case 1:
                                String newName = Helper.inputString("Enter New Name: ");
                                customerToUpdate.setName(newName);
                                customerService.updateCustomer(customerToUpdate);
                                break;
                            case 2:
                                String newPassword = Helper.inputString("Enter New Password: ");
                                customerToUpdate.setPassword(newPassword);
                                customerService.updateCustomer(customerToUpdate);
                                break;
                            case 3:
                                String newMail = Helper.inputString("Enter New Email: ");
                                customerToUpdate.setEmail(newMail);
                                customerService.updateCustomer(customerToUpdate);
                                break;
                            case 4:
                                long newContactNo = Helper.inputLong("Enter new contact no.: ");
                                customerToUpdate.setContactNo(newContactNo);
                                customerService.updateCustomer(customerToUpdate);
                                break;
                        }
                    } else {
                        Helper.printColored("Error: USER NOT FOUND", "red");
                    }
                    break;
                case 5:
                    return;
            }
        }
    }

    public void ManageItemMenu() {
        while (true) {
            System.out.println("1.List All Items");
            System.out.println("2.Add new Item");
            System.out.println("3.Delete Item");
            System.out.println("4.Update Item");
            System.out.println("5.Buy Item");
            System.out.println("6.View Item History");

            System.out.println("7.Back to Main Menu");
            int choice = Helper.intInput("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1:
                    itemService.display();
                    break;
                case 2:
                    String itemName = Helper.inputString("Enter Item Name: ");
                    int itemPrice = Helper.intInput("Enter item price: ", 1, Integer.MAX_VALUE);
                    int itemQuantity = Helper.intInput("Enter item quantity: ", 1, Integer.MAX_VALUE);

                    itemService.addItem(itemName, itemPrice, itemQuantity);
                    break;
                case 3:
                    String itemNameToDelete = Helper.inputString("Enter Item Name to DELETE: ");

                    itemService.removeItem(itemNameToDelete);
                    break;
                case 4:
                    String itemNameToUpdate = Helper.inputString("Enter Item Name to Update: ");

                    if (itemService.findItemByItemName(itemNameToUpdate)) {
                        Item i = itemService.getItemByItemName(itemNameToUpdate);

                        if (i == null) {
                            Helper.printColored("Error reading item data", "red");
                            return;
                        }

                        System.out.println("1.Change Item Name");
                        System.out.println("2.Change Item Price");
                        System.out.println("3.Change Item Quantity");
                        int ch = Helper.intInput("Enter your choice: ", 1, 3);

                        switch (ch) {
                            case 1:
                                String newName = Helper.inputString("Enter Item Name to update: ");
                                i.setItemName(newName);
                                itemService.updateItem(i);
                                break;
                            case 2:
                                int newPrice = Helper.intInput("Enter new price: ", 1, Integer.MAX_VALUE);
                                i.setItemPrice(newPrice);
                                itemService.updateItem(i);
                                break;
                            case 3:
                                int newQuantity = Helper.intInput("Enter new quantity: ", 1, Integer.MAX_VALUE);
                                i.setQuantity(newQuantity);
                                itemService.updateItem(i);
                                break;
                            default:
                                Helper.printColored("Invalid Choice", "red");
                                break;
                        }
                    } else {
                        Helper.printColored("Error: ITEM NOT FOUND NOT FOUND", "red");
                    }
                    break;
                case 5:
                    String itemNameToBuy = Helper.inputString("Enter item name to Buy: ");

                    Item i = itemService.getItemByItemName(itemNameToBuy);
                    int accountBalance = accountService.getBalance();

                    if (i == null) {
                        Helper.printColored("Error reading item data.", "red");
                        return;
                    }

                    int quantity = Helper.intInput("Enter quantity: ", 1, Integer.MAX_VALUE);
                    int priceFromMarket = (int) (i.getItemPrice() * 0.9);
                    int totalPrice = quantity * priceFromMarket;
                    System.out.println("Total price: " + totalPrice);

                    if (accountBalance < totalPrice) {
                        Helper.printColored("Not enough balance.", "red");
                        return;
                    }

                    accountService.decreaseAmount(totalPrice);
                    i.setQuantity(i.getQuantity() + quantity);
                    itemService.updateItem(i);

                    Helper.printColored("ITEM BOUGHT SUCCESSFULLY.", "green");

                    break;
                case 6:
                    String itemNameToDisplayHistory = Helper.inputString("Enter item name to display history: ");
                    Item temp = itemService.getItemByItemName(itemNameToDisplayHistory);
                    if (temp == null) {
                        Helper.printColored("Error could not find item", "red");
                        break;
                    }
                    itemHistoryService.displayItemHistoryByItemId(temp.getId());

                    break;
                case 7:
                    Helper.printColored("Going to main menu....", "grey");
                    return;

                default:
                    Helper.printColored("Invalid Choice", "red");
                    break;
            }
        }
    }

    private Admin login() {
        for (int i = 0; i < 3; i++) {
            String username = Helper.inputString("Enter username: ");
            String password = Helper.inputString("Enter password: ");

            if (adminService.verifyAdminLogin(username, password)) {
                return adminService.getAdminByUsername(username);
            } else if (i == 2) {
                Helper.printColored("TOO MANY WRONG ATTEMPTS\nLOGGING OUT", "red");
                return null;
            }

            Helper.printColored("Invalid Login Credentails\nTRY AGAIN.", "red");
        }
        return null;

    }

    public void AdminMenu() {
        while (true) {
            Helper.printColored("Welcome " + currentAdmin.getUsername().toUpperCase(), "green");
            System.out.println("1.Manage Admin");
            System.out.println("2.Manage Customer");
            System.out.println("3.Manage Items");
            System.out.println("4.View Account Balance");
            System.out.println("5.View Purchase History");
            System.out.println("6.Change Password");
            System.out.println("7.Log Out");
            int choice = Helper.intInput("Enter your choice: ", 1, 7);

            switch (choice) {
                case 1:
                    ManageAdminMenu();
                    break;
                case 2:
                    ManageCustomerMenu();
                    break;
                case 3:
                    ManageItemMenu();
                    break;
                case 4:
                    System.out.println("Current Balance: " + accountService.getBalance());
                    break;
                case 5:
                    Helper.printColored("PURCHASE HISTORY", "blue");
                    itemHistoryService.displayItemHistory();
                    break;
                case 6:
                    String newPassword = Helper.inputString("Enter new Password: ");
                    currentAdmin.setPassword(newPassword);
                    adminService.updateAdmin(currentAdmin);
                    break;
                case 7:
                    System.out.println("Going Back...");
                    return;
            }
        }
    }
}
