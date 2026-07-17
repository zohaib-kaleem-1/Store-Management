package com.store.GUI.controllers.AdminControllers.ManageAdmin;

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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManageAdminController {
    @FXML
    private TableView<User> adminTable;

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
    private TextField searchUsernameField;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    ObservableList<User> adminList = FXCollections.observableArrayList();
    UserService userService = new UserService();

    @FXML
    public void initialize() {
        rowCountComboBox.getItems().addAll(10, 20, 30, 50, 100);
        rowCountComboBox.setValue(20);

        pagination.setMaxPageIndicatorCount(userService.getRowCount("admin"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        editColumn.setCellFactory(event -> new TableCell<>() {
            private final Button editButton = new Button("Update");
            {
                editButton.setOnAction(event -> {
                    SceneManager.switchScene("/com/store/views/adminviews/manageadmin/updateadminview.fxml",
                            "Update Admin", getTableView().getItems().get(getIndex()));
                }

                );
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

        adminList.clear();
        try {

            UserService adminService = new UserService();
            adminList.addAll(adminService.getAllUserByRole("admin"));
        } catch (SQLException e) {
            MessageUtil.showError("User Data Reading error", e.getMessage());
        }

        adminTable.setItems(adminList);
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void addAdmin() {
        SceneManager.switchScene("/com/store/views/adminviews/manageadmin/addadminview.fxml", "Add Admin");
    }
}
