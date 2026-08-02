package com.store.GUI.controllers.CustomerControllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.ResourceBundle;

public class OrderController {
    @FXML
    private TextField searchField;

    @FXML
    private Label statusLabel;

    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<String> orderStatusComboBox;
    @FXML
    private ComboBox<Integer> rowCountComboBox;
    @FXML
    private TreeTableView<Object> orderTable;

    // TreeTableColumns for Order
    @FXML
    private TreeTableColumn<Object, Integer> orderIdColumn;
    @FXML
    private TreeTableColumn<Object, Timestamp> boughtAtColumn;
    @FXML
    private TreeTableColumn<Object, String> addressColumn;
    @FXML
    private TreeTableColumn<Object, String> statusColumn;
    @FXML
    private TreeTableColumn<Object, Double> totalPriceColumn;

    // TreeTableColumns for OrderItem
    @FXML
    private TreeTableColumn<Object, Integer> itemIdColumn;
    @FXML
    private TreeTableColumn<Object, String> itemNameColumn;
    @FXML
    private TreeTableColumn<Object, Double> priceColumn;
    @FXML
    private TreeTableColumn<Object, Integer> quantityColumn;
    @FXML
    private TreeTableColumn<Object, Double> subtotalColumn;

    // Summary Labels
    @FXML
    private Label totalOrdersLabel;
    @FXML
    private Label totalItemsLabel;
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
        setupSearchListener();
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
        statusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderStatus"));
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
        rowCountComboBox.getItems().addAll(5, 10, 20, 30, 50, 100);
        rowCountComboBox.setValue(20);

        // Order Status options
        orderStatusComboBox.getItems().addAll("All Orders", "pending", "ready", "shipped", "delivered", "cancelled");
        orderStatusComboBox.setValue("All Orders");

        // Add listeners
        orderStatusComboBox.valueProperty().addListener((obs, old, newVal) -> {
            updatePageCount();
            fetchData();
        });

        rowCountComboBox.valueProperty().addListener((obs, old, newVal) -> {
            updatePageCount();
            fetchData();
        });

        pagination.currentPageIndexProperty().addListener((obs, old, newVal) -> {
            fetchData();
        });
    }

    private void setupSearchListener() {
        searchField.textProperty().addListener((event) -> {
            updatePageCount();
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
        int totalItems = 0;
        double totalRevenue = 0.0;

        for (Order order : orderList) {
            if (order.getItemList() != null) {
                totalItems += order.getItemList().size();
            }
            totalRevenue += order.getTotalPriceOfAllItem();
        }

        totalOrdersLabel.setText(String.valueOf(totalOrders));
        totalItemsLabel.setText(String.valueOf(totalItems));
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

    @FXML
    public void viewOrderDetails() {
        try {
            TreeItem<Object> selectedItem = orderTable.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                MessageUtil.showError("View Details", "Please select an order to view details");
                return;
            }

            Object value = selectedItem.getValue();

            if (value instanceof Order) {
                Order order = (Order) value;
                // Show order details
                showOrderDetailsDialog(order);
            } else if (value instanceof OrderItem) {
                OrderItem item = (OrderItem) value;
                // Find the parent order
                TreeItem<Object> parent = selectedItem.getParent();
                if (parent != null && parent.getValue() instanceof Order) {
                    Order order = (Order) parent.getValue();
                    showOrderDetailsDialog(order);
                }
            }

        } catch (Exception e) {
            MessageUtil.showError("View Details", e.getMessage());
        }
    }

    private void showOrderDetailsDialog(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("Order Details\n");
        details.append("=============\n\n");
        details.append("Order ID: #").append(order.getOrderId()).append("\n");
        details.append("Date: ").append(formatTimestamp(order.getBought_at())).append("\n");
        details.append("Status: ").append(order.getOrderStatus()).append("\n");
        details.append("Address: ").append(order.getAddress()).append("\n");
        details.append("Total: $").append(String.format("%.2f", order.getTotalPriceOfAllItem())).append("\n\n");
        details.append("Items:\n");
        details.append("------\n");

        if (order.getItemList() != null) {
            for (OrderItem item : order.getItemList()) {
                details.append("- ").append(item.getItemName())
                        .append(" x").append(item.getQuantity())
                        .append(" ($").append(String.format("%.2f", item.getPrice()))
                        .append(" each) = $").append(String.format("%.2f", item.getTotalPrice()))
                        .append("\n");
            }
        }

        MessageUtil.showMessage("Order Details", details.toString());
    }

    private String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null)
            return "N/A";
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}