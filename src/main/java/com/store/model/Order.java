package com.store.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private String customerName;
    private int customerId;
    private int totalPrice;
    private Timestamp bought_at;
    private List<Item> itemList;
    private String orderStatus;
    private String address;

    // when user buys something
    public Order(int customerId, String customerName, int totalPrice, List<Item> itemList, String orderStatus,
            String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.itemList = itemList;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    // when loading data from database
    public Order(int orderId, int customerId, String customerName, int totalPrice, List<Item> itemList,
            Timestamp bought_at, String orderStatus, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.itemList = itemList;
        this.orderId = orderId;
        this.bought_at = bought_at;

        this.address = address;
        this.orderStatus = orderStatus;
    }

    public Timestamp getBought_at() {
        return bought_at;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setBought_at(Timestamp bought_at) {
        this.bought_at = bought_at;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
