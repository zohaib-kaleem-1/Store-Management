package com.store.GUI.controllers.CustomerControllers;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.store.Transaction.Transaction;
import com.store.Util.MessageUtil;
import com.store.Util.SceneManager;
import com.store.Util.SessionManager;
import com.store.model.Order;
import com.store.model.OrderItem;
import com.store.service.OrderService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class OrderController {

    @FXML
    private ComboBox<String> orderStatusComboBox;
    @FXML
    private ComboBox<Integer> rowCountComboBox;

    @FXML
    private TreeTableView<Object> orderTable;

    // Parent column
    @FXML
    private TreeTableColumn<Object, Integer> orderIdColumn;

    @FXML
    private TreeTableColumn<Object, Timestamp> boughtAtColumn;

    @FXML
    private TreeTableColumn<Object, String> addressColumn;

    @FXML
    private TreeTableColumn<Object, String> orderStatusColumn;

    @FXML
    private TreeTableColumn<Object, Integer> totalPriceColumn;

    // Child Columns
    @FXML
    private TreeTableColumn<Object, Integer> itemIdColumn;
    @FXML
    private TreeTableColumn<Object, String> itemNameColumn;
    @FXML
    private TreeTableColumn<Object, Integer> priceColumn;
    @FXML
    private TreeTableColumn<Object, Integer> quantityColumn;
    @FXML
    private TreeTableColumn<Object, Integer> subtotalColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalOrdersLabel;

    @FXML
    private Label totalRevenueLabel;
    // Service and Data
    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private OrderService orderService = new OrderService();
    private int currentUserId = SessionManager.getUser().getId();

    @FXML
    public void initialize() {
        setupTableColumns();
        setupComboBoxes();
        updatePageCount();
        fetchData();

        // Enable single selection
        orderTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void setupTableColumns() {
        // Order level columns
        orderIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        boughtAtColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("boughtAt"));
        addressColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        orderStatusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderStatus"));
        totalPriceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPriceOfAllItem"));

        // OrderItem level columns
        itemIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPrice"));
    }

    private void setupComboBoxes() {
        // Rows per page
        rowCountComboBox.getItems().addAll(10, 20, 30, 50, 100);
        rowCountComboBox.setValue(20);

        // Order Status options
        orderStatusComboBox.getItems().addAll("All Orders", "pending", "ready", "shipped", "delivered");
        orderStatusComboBox.setValue("All Orders");

        // Add listeners
        orderStatusComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        rowCountComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        searchField.textProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        pagination.currentPageIndexProperty().addListener(event -> {
            fetchData();
        });
    }

    private void fetchData() {
        orderList.clear();

        try {
            String orderStatus = orderStatusComboBox.getValue().equals("All Orders")
                    ? ""
                    : orderStatusComboBox.getValue();

            String orderId = searchField.getText() == null || searchField.getText().isEmpty()
                    ? ""
                    : searchField.getText();

            int pageSize = rowCountComboBox.getValue();
            int pageIndex = pagination.getCurrentPageIndex();

            // Fetch orders with pagination
            orderList.addAll(orderService.listOrderByCustomerId(
                    currentUserId,
                    orderId,
                    orderStatus,
                    pageSize,
                    pageIndex));

            buildTreeTable();
            updateSummary();

            statusLabel.setText("Orders loaded successfully.");

        } catch (SQLException e) {
            MessageUtil.showError("Order Data Reading Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void updatePageCount() {
        try {
            String orderStatus = orderStatusComboBox.getValue().equals("All Orders")
                    ? ""
                    : orderStatusComboBox.getValue();

            String orderId = searchField.getText() == null || searchField.getText().isEmpty()
                    ? ""
                    : searchField.getText();

            int totalRows = orderService.getRowCountForUser(currentUserId, orderStatus, orderId);

            int pageLimit = rowCountComboBox.getValue();
            int pageCount = (int) Math.ceil((float) totalRows / (float) pageLimit);

            pagination.setPageCount(pageCount > 0 ? pageCount : 1);
            pagination.setCurrentPageIndex(0);

        } catch (Exception e) {
            MessageUtil.showError("Error", e.getMessage());
        }
    }

    private void buildTreeTable() {
        // Create root node (invisible)
        TreeItem<Object> root = new TreeItem<>("Root");
        root.setExpanded(true);

        // Add each order as a parent node
        for (Order order : orderList) {
            // Create order node
            TreeItem<Object> orderNode = new TreeItem<>(order);
            orderNode.setExpanded(false);

            // Add order items as child nodes
            if (order.getItemList() != null && !order.getItemList().isEmpty()) {
                for (OrderItem item : order.getItemList()) {
                    TreeItem<Object> itemNode = new TreeItem<>(item);
                    orderNode.getChildren().add(itemNode);
                }
            }

            root.getChildren().add(orderNode);
        }

        // Set root and hide it
        orderTable.setRoot(root);
        orderTable.setShowRoot(false);
    }

    private void updateSummary() {
        int totalOrders = orderList.size();
        double totalRevenue = 0.0;

        for (Order order : orderList) {
            totalRevenue += order.getTotalPriceOfAllItem();
        }

        totalOrdersLabel.setText(String.valueOf(totalOrders));
        totalRevenueLabel.setText(String.format("$%.2f", totalRevenue));
    }

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void refreshOrders() {
        fetchData();
        MessageUtil.showMessage("Refresh", "Orders refreshed successfully!");
    }

    @FXML
    public void cancelOrder() {
        try {
            TreeItem<Object> selectedItem = orderTable.getSelectionModel().getSelectedItem();

            if (selectedItem == null || !(selectedItem.getValue() instanceof Order)) {
                MessageUtil.showError("Cancel Order", "Please select an order to cancel");
                return;
            }

            Order order = (Order) selectedItem.getValue();

            // Check if order can be cancelled
            if (!order.getOrderStatus().equalsIgnoreCase("pending")) {
                MessageUtil.showError("Cancel Order",
                        "Cannot cancel this order. Only pending orders can be cancelled.");
                return;
            }

            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Order");
            alert.setHeaderText("Confirm Cancellation");
            alert.setContentText("Are you sure you want to cancel Order #" + order.getOrderId() + "?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (Transaction.cancelOrder(order.getOrderId())) {
                            MessageUtil.showMessage("Order Manager", "Order cancelled successfully!");
                            refreshOrders();
                        }
                    } catch (Exception e) {
                        MessageUtil.showError("Order Manager", e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            MessageUtil.showError("Order Error", e.getMessage());
        }
    }
}