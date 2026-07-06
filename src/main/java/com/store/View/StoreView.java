package com.store.View;

import com.store.HelperFunction.Helper;

public class StoreView {
    public void displayMenu() {
        while (true) {
            System.out.println("1.Admin View");
            System.out.println("2.Customer Menu");
            System.out.println("3.Exit");
            int choice = Helper.intInput("Enter your choice: ", 1, 3);

            switch (choice) {
                case 1:
                    new AdminView();
                    break;
                case 2:
                    new CustomerView();
                    break;
                case 3:
                    System.out.println("Exiting.....");
                    return;
                default:
                    Helper.printColored("Invalid Choice", "red");
                    break;
            }

        }
    }
}
