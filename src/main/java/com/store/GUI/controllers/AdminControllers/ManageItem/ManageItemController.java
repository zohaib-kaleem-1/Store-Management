package com.store.GUI.controllers.AdminControllers.ManageItem;

import com.store.model.Item;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
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

public class ManageItemController {
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
    private Label statusLabel;

    @FXML
    private TextField searchItemNameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

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

        searchItemNameField.setOnAction(event -> {
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
            itemList.addAll(itemService.display(
                    searchItemNameField.getText() != null ? searchItemNameField.getText().trim() : "",
                    rowCountComboBox.getValue(), pagination.getCurrentPageIndex()));

            statusLabel.setText("Data loaded successfully.");
        } catch (SQLException e) {
            MessageUtil.showError("Item Data Reading error", e.getMessage());
            statusLabel.setText("Failed to load data.");
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
    public void goToAddItem() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/addupdateitemview.fxml", "Add Item", null);
    }

    @FXML
    private void goToUpdateItem() {
        ObservableList<Item> selectedItem = itemTable.getSelectionModel().getSelectedItems();

        if (selectedItem == null || selectedItem.isEmpty()) {
            MessageUtil.showError("Item Update Manager", "No item selected yet");
            return;
        }

        SceneManager.switchScene("/com/store/views/adminviews/manageitem/addupdateitemview.fxml", "Update Item",
                selectedItem.get(0));
    }

    @FXML
    public void deleteItem() {
        try {
            ObservableList<Item> selectedItem = itemTable.getSelectionModel().getSelectedItems();

            if (selectedItem == null || selectedItem.isEmpty())
                MessageUtil.showError("Item Update Manager", "No item selected yet");

            new ItemService().removeItem(selectedItem.get(0).getId());

            MessageUtil.showMessage("Item Manager", "Item deleted successfully.");
            refreshItems();
        } catch (Exception e) {
            MessageUtil.showError("Item Manager", e.getMessage());
        }
    }

    @FXML
    public void refreshItems() {
        fetchData();
    }
}