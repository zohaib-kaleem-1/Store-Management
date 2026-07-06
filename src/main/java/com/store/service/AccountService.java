package com.store.service;

import com.store.HelperFunction.Helper;
import com.store.dao.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public int getBalance() {
        return accountDAO.getAccountBalance();
    }

    public void setAccountBalance(int newBalance) {
        if (accountDAO.updateAccountBalance(newBalance)) {
            Helper.printColored("Balance updated successfully.", "green");
        } else {
            Helper.printColored("Error updating balance.", "red");
        }
    }

    public void increaseAmount(int price) {
        if (accountDAO.updateAccountBalance(accountDAO.getAccountBalance() + price)) {
            Helper.printColored("Balance updated successfully.", "green");
        } else {
            Helper.printColored("Error updating balance.", "red");
        }
    }

    public void decreaseAmount(int price) {
        if (accountDAO.updateAccountBalance(accountDAO.getAccountBalance() - price)) {
            Helper.printColored("Balance updated successfully.", "green");
        } else {
            Helper.printColored("Error updating balance.", "red");
        }
    }
}
