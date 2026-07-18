package com.store.GUI.controllers.AdminControllers.ManageItem;

import com.store.model.Item;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.service.ItemService;
import com.store.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
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
    private Button addItemButton;

    @FXML
    private TextField searchItemNameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    ObservableList<Item> itemList = FXCollections.observableArrayList();
    ItemService itemService = new ItemService();

    @FXML
    public void initialize() {
        // rows per page
        rowCountComboBox.getItems().addAll( 20, 30, 50, 100);

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
    }

    private void fetchData() {
        itemList.clear();
        try {
            itemList.addAll(itemService.display(
                    searchItemNameField.getText() != null ? searchItemNameField.getText().trim() : "",
                    rowCountComboBox.getValue(), pagination.getCurrentPageIndex()));
        } catch (SQLException e) {
            MessageUtil.showError("User Data Reading error", e.getMessage());
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
    public void addItem() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/additemview.fxml", "Add Admin");
    }
}
