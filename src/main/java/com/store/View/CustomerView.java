package com.store.View;

import java.util.ArrayList;
import java.util.List;

import com.store.HelperFunction.Helper;
import com.store.model.*;
import com.store.service.*;

public class CustomerView {
    Customer currentCustomer;

    CustomerService customerService;
    ItemService itemService;
    ItemHistoryService itemHistoryService;
    AccountService accountService;

    public CustomerView() {
        this.customerService = new CustomerService();
        this.itemService = new ItemService();
        this.itemHistoryService = new ItemHistoryService();
        this.accountService = new AccountService();

        System.out.println("1.Login");
        System.out.println("2.Register");
        int choice = Helper.intInput("Enter your choice: ", 1, 2);

        if (choice == 1)
            this.currentCustomer = login();
        else if (choice == 2) {
            Register();
            this.currentCustomer = login();
        }

        if (currentCustomer != null)
            CustomerMenu();
    }

    private void Register() {
        String username, password, email, name;
        while (true) {
            username = Helper.inputString("Enter username: ");

            if (customerService.findCustomerByUsername(username)) {
                Helper.printColored("USERNAME already choosen choose another please", "red");
            } else
                break;
        }

        password = Helper.inputString("Enter password: ");
        name = Helper.inputString("Enter name: ");
        email = Helper.inputString("Enter email: ");
        long contactNo = Helper.inputLong("Enter contactNo: ");

        customerService.addCustomer(name, username, password, email, contactNo);
    }

    private Customer login() {
        System.out.println("\n\n LOGIN PAGE \n------------------------------------");
        for (int i = 0; i < 3; i++) {
            String username = Helper.inputString("Enter username: ");
            String password = Helper.inputString("Enter password: ");

            if (customerService.verifyLogin(username, password)) {
                return customerService.getCustomerByUsername(username);
            }

            Helper.printColored("Invalid Login Credentails\nTRY AGAIN.", "red");
        }

        Helper.printColored("TOO MANY WRONG ATTEMPTS\nLOGGING OUT", "red");
        return null;
    }

    private void BuyItemsMenu() {
        Helper.printColored("ITEMS LIST", "blue");
        System.out.println("----------------------------------------------");

        if (!itemService.displayAvailableItems()) {
            System.out.println("Wait for admin to add items.");
            System.out.println("Come back soon");
            return;
        }

        String itemToBuy = Helper.inputString("Enter name of item to buy: ");

        Item item = itemService.getItemByItemName(itemToBuy);
        if (item == null) {
            Helper.printColored("Invalid choice", "red");
            return;
        }

        int quantity = Helper.intInput("Enter quantity of item: ", 1, item.getQuantity());

        item.setQuantity(item.getQuantity() - quantity);
        int price = quantity * item.getItemPrice();

        itemHistoryService.addItemHistory(item.getId(), currentCustomer.getId(), quantity, price);
        itemService.updateItem(item);
        accountService.increaseAmount(price);

    }

    private void ManageAccountMenu() {
        Helper.printColored("CUSTOMER DETAILS", "blue");
        System.out.println("----------------------------------------------");

        List<Customer> a = new ArrayList<>();
        a.add(currentCustomer);
        Helper.printList(a);

        System.out.println("1.Change name");
        System.out.println("2.Change password");
        System.out.println("3.Change email");
        System.out.println("4.Change contact no. ");
        System.out.println("5.Go Back To Main Menu");
        int choice = Helper.intInput("Enter your choice: ", 1, 5);

        switch (choice) {
            case 1:
                String newName = Helper.inputString("Enter new name: ");
                currentCustomer.setName(newName);
                customerService.updateCustomer(currentCustomer);
                break;
            case 2:

                String newPassword = Helper.inputString("Enter new password: ");
                currentCustomer.setPassword(newPassword);
                customerService.updateCustomer(currentCustomer);
                break;
            case 3:

                String newMail = Helper.inputString("Enter new email: ");
                currentCustomer.setEmail(newMail);
                customerService.updateCustomer(currentCustomer);
                break;
            case 4:
                long newContactNo = Helper.inputLong("Enter new contact no.: ");
                currentCustomer.setContactNo(newContactNo);
                customerService.updateCustomer(currentCustomer);
                break;
            case 5:
                System.out.println("Going back.....");
                return;
        }
    }

    private void CustomerMenu() {
        while (true) {
            Helper.printColored("WELCOME " + currentCustomer.getName().toUpperCase(), "green");
            System.out.println("1.Buy Items");
            System.out.println("2.Manage Your Account");
            System.out.println("3.View Purchase History");
            System.out.println("4.Log Out");
            int choice = Helper.intInput("Enter your choice: ", 1, 4);

            switch (choice) {
                case 1:
                    BuyItemsMenu();
                    break;
                case 2:
                    ManageAccountMenu();
                    break;
                case 3:
                    Helper.printColored("PURCHASE HISTORY", "blue");
                    itemHistoryService.displayItemHistoryByCustomerId(currentCustomer.getId());
                    break;
                case 4:
                    Helper.printColored("Going Back...", "green");
                    return;

                default:
                    Helper.printColored("Invalid Choice", "red");
                    break;
            }
        }

    }
}
