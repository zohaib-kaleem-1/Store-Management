package com.store.GUI.controllers.CustomerControllers.Cart;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.CartItem;
import com.store.service.CartService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartController {
    @FXML
    private TableView<CartItem> itemTable;

    @FXML
    private TableColumn<CartItem, String> nameColumn;
    @FXML
    private TableColumn<CartItem, String> priceColumn;
    @FXML
    private TableColumn<CartItem, String> quantityColumn;
    @FXML
    private TableColumn<CartItem, String> quantityInStoreColumn;
    @FXML
    private TableColumn<CartItem, String> totalPriceColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchItemNameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    ObservableList<CartItem> itemList = FXCollections.observableArrayList();
    CartService cartService = new CartService();

    @FXML
    public void initialize() {
        // rows per page
        rowCountComboBox.getItems().addAll(20, 30, 50, 100);

        // default number of rows
        rowCountComboBox.setValue(20);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceOfEachItem"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityInStoreColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStore"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        rowCountComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        searchItemNameField.textProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        pagination.currentPageIndexProperty().addListener(event -> {
            fetchData();
        });

        updatePageCount();
        fetchData();

        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void fetchData() {
        itemList.clear();
        try {
            itemList.addAll(cartService.listFromCartByCustomerId(SessionManager.getUser().getId(),
                    searchItemNameField.getText().trim(), rowCountComboBox.getValue(),
                    pagination.getCurrentPageIndex()));
        } catch (SQLException e) {
            MessageUtil.showError("Cart Data Reading error", e.getMessage());
        }

        itemTable.setItems(itemList);
    }

    private void updatePageCount() {
        try {
            int totalRows = cartService.getRowCount(SessionManager.getUser().getId(), searchItemNameField.getText());
            int pageLimit = rowCountComboBox.getValue();
            int pageCount = (int) Math.ceil((float) totalRows / (float) pageLimit);
            pagination.setPageCount(pageCount);
            pagination.setCurrentPageIndex(0);
        } catch (Exception e) {
            MessageUtil.showError("Manage Item", e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void buyItems() {
        System.out.println("Item Bought");
    }
}
