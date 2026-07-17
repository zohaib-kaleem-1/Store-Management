package com.store.GUI.controllers.Cart;

import com.store.model.CartItem;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.service.CartService;
import com.store.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartViewController {
    @FXML
    private TableView<CartItem> itemTable;

    @FXML
    private TableColumn<CartItem, Integer> idColumn;
    @FXML
    private TableColumn<CartItem, String> nameColumn;
    @FXML
    private TableColumn<CartItem, Integer> priceColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityColumn;
    @FXML
    private TableColumn<CartItem, Integer> quantityInStoreColumn;
    @FXML
    private TableColumn<CartItem, Integer> totalPriceColumn;
    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableColumn<CartItem, Void> updateColumn;

    ObservableList<CartItem> itemList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityInStoreColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStore"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        updateColumn.setCellFactory(event -> new TableCell<>() {
            private final Button editButton = new Button("Update");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/cart/editCartDialogue.fxml",
                            "Update Cart", getTableView().getItems().get(getIndex()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        itemList.clear();

        try {

            CartService cartService = new CartService();
            itemList.addAll(cartService.listFromCartByCustomerId(SessionManager.getUser().getId()));
        } catch (SQLException e) {
            MessageUtil.showError("Cart Reader", e.getMessage());
        }
        itemTable.setItems(itemList);

        calculateTotalPrice();
    }

    @FXML
    public void buyItem() {
        String role = SessionManager.getUser().getRole().toLowerCase();
        int id = SessionManager.getUser().getId();

        try {
            UserService userService = new UserService();
            userService.buyItem(id, role);

            MessageUtil.showMessage("Buy Item", "Items bought successfully.");
            goBack();
        } catch (Exception e) {
            MessageUtil.showError("Error", e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    public void calculateTotalPrice() {
        int totalPrice = 0;

        for (CartItem i : itemList) {
            totalPrice += i.getTotalPrice();
        }

        totalPriceLabel.setText("Total Price: " + String.valueOf(totalPrice));
    }
}
