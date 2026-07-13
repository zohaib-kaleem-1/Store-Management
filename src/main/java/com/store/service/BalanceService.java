package com.store.service;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import com.store.model.Balance;

public class BalanceService implements AutoCloseable {
    Balance balance = new Balance();

    public BalanceService() {
        loadBalanceData();
    }

    public void loadBalanceData() {
        File myFile = new File("src/main/java/com/store/data/balance.sdk");

        try (Scanner src = new Scanner(myFile)) {
            String data = src.nextLine();
            balance.setBalance(Integer.parseInt(data));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            balance.setBalance(100);
        }
    }

    public void saveBalanceData() {
        try (FileWriter fileWriter = new FileWriter("src/main/java/com/store/data/balance.sdk")) {
            fileWriter.write(String.valueOf(balance.getBalance()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void increaseBalance(int newBalance) {
        balance.increaseBalance(newBalance);
    }

    public void decreaseBalance(int newBalance) {
        balance.decreaseBalance(newBalance);
    }

    public int getBalance() {
        return balance.getBalance();
    }

    public void setBalance(int newBalance) {
        balance.setBalance(newBalance);
    }

    public void close() {
        saveBalanceData();
    }
}
