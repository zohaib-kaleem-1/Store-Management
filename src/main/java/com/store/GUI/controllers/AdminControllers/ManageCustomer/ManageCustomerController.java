package com.store.GUI.controllers.AdminControllers.ManageCustomer;

import com.store.model.User;
import com.store.Util.SceneManager;
import com.store.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageCustomerController {
    @FXML
    private TableView<User> customerTable;

    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> contactColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, Void> editColumn;

    ObservableList<User> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        editColumn.setCellFactory(event -> new TableCell<>() {
            private final Button editButton = new Button("Update");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/adminviews/managecustomer/updatecustomerview.fxml",
                            "Update Customer", getTableView().getItems().get(getIndex()));
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

        customerList.clear();
        UserService customerService = new UserService();

        customerList.addAll(customerService.getAllUserByRole("customer"));

        customerTable.setItems(customerList);
    }

    @FXML
    public void goToDashboard() {
        SceneManager.goToDashboard();
    }

    @FXML
    public void addCustomer() {
        SceneManager.switchScene("/com/store/views/adminviews/managecustomer/addcustomerview.fxml", "Add Admin");
    }

}
