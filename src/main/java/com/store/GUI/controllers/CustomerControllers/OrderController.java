package com.store.GUI.controllers.CustomerControllers;

import java.io.ObjectOutput;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class OrderController {
    @FXML
    private Pagination pagination;

    @FXML
    private ComboBox<String> orderStatusComboBox;

    @FXML
    private ComboBox<Integer> rowCountComboBox;

    @FXML
    private TreeTableView<Object> orderTable;

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

    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private OrderService orderService = new OrderService();
    private int currentUserId = SessionManager.getUser().getId();

    @FXML
    public void goBack() {
        SceneManager.goBack();
    }

    @FXML
    public void initialize() {
        // Setup columns
        orderIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        boughtAtColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("bought_at"));
        addressColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        orderStatusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderStatus"));
        totalPriceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPriceOfAllItem"));

        // ===== ORDERITEM COLUMNS (Child Level) =====
        itemIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        subtotalColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPrice"));

        // rows per page
        rowCountComboBox.getItems().addAll(20, 30, 50, 100);

        // default number of rows
        rowCountComboBox.setValue(20);

        // OrderStatus
        orderStatusComboBox.getItems().addAll("All Orders", "pending", "ready", "shipped", "delivered");

        // default value
        orderStatusComboBox.setValue("All Orders");

        orderStatusComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        rowCountComboBox.valueProperty().addListener(event -> {
            updatePageCount();
            fetchData();
        });

        pagination.currentPageIndexProperty().addListener(event -> {
            fetchData();
        });

        updatePageCount();
        fetchData();

        orderTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void fetchData() {
        orderList.clear();

        try {
            String orderStatusToShow = orderStatusComboBox.getValue().equals("All Orders")
                    ? ""
                    : orderStatusComboBox.getValue();

            orderList.addAll(orderService.listOrderByCustomerId(
                    currentUserId,
                    orderStatusToShow,
                    rowCountComboBox.getValue(),
                    pagination.getCurrentPageIndex()));

            buildTreeTable();

        } catch (SQLException e) {
            MessageUtil.showError("Order Data Reading Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void updatePageCount() {
        try {
            String orderStatusToShow = orderStatusComboBox.getValue().matches("All Orders") ? ""
                    : orderStatusComboBox.getValue();

            int totalRows = orderService.getRowCountForUser(currentUserId, orderStatusToShow);

            int pageLimit = rowCountComboBox.getValue();
            int pageCount = (int) Math.ceil((float) totalRows / (float) pageLimit);
            pagination.setPageCount(pageCount);
            pagination.setCurrentPageIndex(0);
        } catch (Exception e) {
            MessageUtil.showError("Manage Item", e.getMessage());
        }
    }

    @FXML
    public void cancelOrder() {
        try {
            TreeItem<Object> selected = orderTable.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getValue() instanceof Order) {
                Order order = (Order) selected.getValue();
                if (order.getOrderStatus().matches("pending")) {
                    if (Transaction.cancelOrder(order.getOrderId())) {
                        MessageUtil.showMessage("Order Manager", "Order cancelled successfully.");
                        fetchData();
                    }
                } else
                    MessageUtil.showError("Order Manager", "Too Late to cancel order");

            } else
                throw new Exception("Please Select any order");
        } catch (Exception e) {
            MessageUtil.showError("Order", e.getMessage());
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
            orderNode.setExpanded(false); // Start collapsed

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

        // Expand the first order if there are orders
        if (!root.getChildren().isEmpty()) {
            root.getChildren().get(0).setExpanded(true);
        }
    }

}
