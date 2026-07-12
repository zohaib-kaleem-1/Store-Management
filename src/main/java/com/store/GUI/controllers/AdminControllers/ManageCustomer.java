package com.store.GUI.controllers.AdminControllers;

import com.store.model.User;
import com.store.Util.SceneManager;
import com.store.service.CustomerService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageCustomer {
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

    ObservableList<User> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        customerList.clear();
        CustomerService customerService = new CustomerService();

        customerList.addAll(customerService.getAllUserByRole("customer"));

        customerTable.setItems(customerList);

    }

    @FXML
    public void goToDashboard() {
        SceneManager.goToDashboard();
    }

    @FXML
    public void addCustomer() {
        System.out.println("adding customer");
    }
}
