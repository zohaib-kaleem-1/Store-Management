package com.store.GUI.controllers.BuyItem;

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

public class BuyItemController {
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
    private TableColumn<Item, Void> buyColumn;

    ObservableList<Item> itemList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        buyColumn.setCellFactory(event -> new TableCell<>() {
            private final Button editButton = new Button("Add To Cart");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/buyitem/addtocartdialogue.fxml",
                            "Add to Cart", getTableView().getItems().get(getIndex()));
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
            MessageUtil.showError("Item Data Reader", e.getMessage());
        }

        itemTable.setItems(itemList);
    }

    @FXML
    public void goToCart() {
        SceneManager.switchScene("/com/store/views/cart/cartview.fxml", "My Cart");
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }
}
