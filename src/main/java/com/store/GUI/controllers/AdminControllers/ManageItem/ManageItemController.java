package com.store.GUI.controllers.AdminControllers.ManageItem;

import com.store.model.Item;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.service.ItemService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageItemController {
    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> priceColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;

    @FXML
    private TableColumn<Item, Void> editColumn;

    ObservableList<Item> itemList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        editColumn.setCellFactory(event -> new TableCell<>() {
            private final Button editButton = new Button("Update");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/adminviews/manageitem/updateitemview.fxml",
                            "Update Item", getTableView().getItems().get(getIndex()));
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
            ItemService itemService = new ItemService();
            itemList.addAll(itemService.display());
        } catch (SQLException e) {
            MessageUtil.showError("Item Data Reading", e.getMessage());
        }

        itemTable.setItems(itemList);
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void addItem() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/additemview.fxml", "Add Item");
    }

    @FXML
    public void goToBuyItemView() {
        SceneManager.switchScene("/com/store/views/buyitem/buyitemview.fxml", null);
    }

}
