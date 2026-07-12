package com.store.GUI.controllers.CustomerControllers;

import com.store.Transaction.Transaction;
import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.CartItem;
import com.store.service.CartService;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartViewController {
    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, String> itemNameColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;

    @FXML
    private TableColumn<CartItem, Integer> quantityInStoreColumn;

    @FXML
    private TableColumn<CartItem, Integer> priceColumn;

    @FXML
    private TableColumn<CartItem, Integer> totalPriceColumn;

    @FXML
    private TableColumn<CartItem, Void> UpdateRemoveColumn;

    @FXML
    private Label totalPriceOfAllItems;

    @FXML
    private Button buyItemsButton;

    ObservableList<CartItem> cartList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityInStoreColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStore"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        cartList.addListener((ListChangeListener<CartItem>) change -> updateTotalPrice());

        cartList.clear();

        CartService cartService = new CartService();
        cartList.addAll(cartService.listFromCartByCustomerId(SessionManager.getUser().getId()));
        cartTable.setItems(cartList);

        buyItemsButton.setOnAction(event -> buyItems());
    }

    public void updateTotalPrice() {
        int totalPrice = 0;

        for (CartItem i : cartList) {
            totalPrice += (i.getQuantity() * i.getPrice());
        }

        totalPriceOfAllItems.setText("Total Price: " + String.valueOf(totalPrice));
    }

    @FXML
    public void buyItems() {
        if (Transaction.buyItems()) {
            MessageUtil.showMessage("Success", "Items Bought Successfully.");
            cartList.clear();
        } else {
            MessageUtil.showError("Error", "Could not buy items");
        }
    }

    @FXML
    public void goToDashboard() {
        SceneManager.goToDashboard();
    }
}
