package com.store.GUI.controllers.CustomerControllers.BuyItem;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.Item;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.ItemService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuyItemController {
    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, String> priceColumn;
    @FXML
    private TableColumn<Item, String> quantityColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchItemNameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    @FXML
    private Label cartCountLabel;

    @FXML
    private Label balanceLabel;

    ObservableList<Item> itemList = FXCollections.observableArrayList();
    ItemService itemService = new ItemService();

    @FXML
    public void initialize() {
        // rows per page
        rowCountComboBox.getItems().addAll(20, 30, 50, 100);

        // default number of rows
        rowCountComboBox.setValue(20);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

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

    @FXML
    public void searchItems() {
        updatePageCount();
        fetchData();
    }

    @FXML
    public void clearSearch() {
        searchItemNameField.setText("");
        updatePageCount();
        fetchData();
    }

    private void fetchData() {
        try {
            // Get cart count
            cartCountLabel.setText(String.valueOf(new CartService().getRowCount(SessionManager.getUser().getId(), "")));

            // Get Balance
            balanceLabel.setText(String.valueOf(new CustomerService().getBalance(SessionManager.getUser().getId())));

            itemList.clear();
            itemList.addAll(itemService.display(
                    searchItemNameField.getText() != null ? searchItemNameField.getText().trim() : "",
                    rowCountComboBox.getValue(), pagination.getCurrentPageIndex()));
        } catch (SQLException e) {
            MessageUtil.showError("Item Data Reading error", e.getMessage());
        }

        itemTable.setItems(itemList);
    }

    private void updatePageCount() {
        try {
            int totalRows = itemService.getRowCount(searchItemNameField.getText());
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
    public void addToCart() {
        ObservableList<Item> selectedItem = itemTable.getSelectionModel().getSelectedItems();

        if (selectedItem == null || selectedItem.isEmpty()) {
            MessageUtil.showError("Item Update Manager", "No item selected yet");
            return;
        }

        SceneManager.switchScene("/com/store/views/customerviews/buyitem/buyitemdialogue.fxml", "Buy Item Dialogue",
                selectedItem.get(0));
    }
}
