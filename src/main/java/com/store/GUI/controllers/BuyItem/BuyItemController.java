package com.store.GUI.controllers.BuyItem;

import com.store.model.Item;
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
            private final Button editButton = new Button("Buy Item");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/buyitem/buyitemdialogue.fxml",
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
        ItemService itemService = new ItemService();

        itemList.addAll(itemService.display());

        itemTable.setItems(itemList);
    }

    @FXML
    public void goToCart() {
        SceneManager.switchScene("/com/store/views/cart/cartview.fxml", "My Cart");
    }

    @FXML
    public void goToDashboard() {
        SceneManager.goToDashboard();
    }

    @FXML
    public void addItem() {
        SceneManager.switchScene("/com/store/views/adminviews/manageitem/additemview.fxml", "Add Item");
    }

    @FXML
    public void goToBuyItemView() {
        SceneManager.switchScene("/com/store/view/adminview/buyitem/buyitemview.fxml", null);
    }

}
