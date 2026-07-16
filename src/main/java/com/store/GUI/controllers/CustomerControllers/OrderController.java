package com.store.GUI.controllers.CustomerControllers;

import java.security.Timestamp;
import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.Order;
import com.store.service.OrderService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderController {
    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> nameColumn;
    @FXML
    private TableColumn<Order, Integer> quantityColumn;
    @FXML
    private TableColumn<Order, Integer> priceColumn;
    @FXML
    private TableColumn<Order, Integer> totalPriceColumn;

    @FXML
    private TableColumn<Order, Timestamp> boughtAtColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    ObservableList<Order> orderList = FXCollections.observableArrayList();

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        boughtAtColumn.setCellValueFactory(new PropertyValueFactory<>("boughtAt"));

        orderList.clear();
        try {
            OrderService orderService = new OrderService();
            orderList.addAll(orderService.listOrderByCustomerId(SessionManager.getUser().getId()));
        } catch (SQLException e) {
            MessageUtil.showError("Order Reader", e.getMessage());
        }

        orderTable.setItems(orderList);
        calculateTotalPrice();
    }

    public void goBack() {
        SceneManager.goBack();
    }

    public void calculateTotalPrice() {
        int totalPrice = 0;

        for (Order i : orderList) {
            totalPrice += i.getTotalPrice();
        }

        totalPriceLabel.setText("Total Price: " + String.valueOf(totalPrice));
    }
}
