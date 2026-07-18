package com.store.GUI.controllers.AdminControllers.ManageUser;

import com.store.model.User;

import java.sql.SQLException;

import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
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

public class ManageUserController implements SceneManager.DataReceiver<String> {
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> contactColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, Void> editColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private Button addUserButton;

    @FXML
    private Label pageTitleLabel;

    @FXML
    private TextField searchUsernameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    ObservableList<User> userList = FXCollections.observableArrayList();
    UserService userService = new UserService();

    String role;

    @FXML
    public void initialize() {
        // rows per page
        rowCountComboBox.getItems().addAll(20, 30, 50, 100);

        // default number of rows
        rowCountComboBox.setValue(20);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        rowCountComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        searchUsernameField.textProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        pagination.currentPageIndexProperty().addListener(event -> {
            fetchData();
        });
    }

    private void fetchData() {
        userList.clear();
        try {
            UserService adminService = new UserService();
            userList.addAll(adminService.getAllUserByRole(
                    searchUsernameField.getText() != null ? searchUsernameField.getText().trim() : "", role,
                    rowCountComboBox.getValue(), pagination.getCurrentPageIndex()));
        } catch (SQLException e) {
            MessageUtil.showError("User Data Reading error", e.getMessage());
        }

        userTable.setItems(userList);
    }

    private void updatePageCount() {
        try {
            int totalRows = userService.getRowCount(searchUsernameField.getText(), role);
            int pageLimit = rowCountComboBox.getValue();
            int pageCount = (int) Math.ceil((float) totalRows / (float) pageLimit);
            pagination.setPageCount(pageCount);
            pagination.setCurrentPageIndex(0);
        } catch (Exception e) {
            MessageUtil.showError("Manage " + role, e.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void addAdmin() {
        SceneManager.switchScene("/com/store/views/adminviews/manageadmin/addadminview.fxml", "Add Admin");
    }

    @Override
    public void setData(String data) {
        System.out.println("data is called");
        this.role = data;
        pageTitleLabel.setText("Manage " + role);
        addUserButton.setText("Add " + role);

        updatePageCount();
        fetchData();
    }
}
