package users.sales;

import classpackage.Customer;
import classpackage.Order;
import classpackage.OrderStatus;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesOrders extends ControllerSales implements Initializable{

    @FXML
    public Button createOrderButton; //Button for creating an order
    public Button deleteOrderButton; //Button for deleting an order
    public TableView ordersTable;
    public BorderPane rootPaneOrders;
    public TableColumn orderIdCol;
    public TableColumn deadlineCol;
    public TableColumn priceCol;
    public TableColumn statusCol;
    private GridPane ordersFormGrid;

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("OrdersCreateForm.fxml"));
                ordersFormGrid = loader.load();
                rootPaneOrders.setCenter(ordersFormGrid);
                rootPaneOrders.setBottom(null);
                rootPaneOrders.setRight(null);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                int selectedIndex = ordersTable.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0){
                    ordersTable.getItems().remove(selectedIndex);
                }else{
                    //Nothing selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No order selected");
                    alert.setContentText("Select an order to delete it");

                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("deleteOrderEvent" + e);
            }
        }
    };

    EventHandler<MouseEvent> ordersInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() >= 1) {
                selectedOrder = (Order)ordersTable.getSelectionModel().getSelectedItem();
                for (Customer customer : customers) {
                    for (Order order : customer.getOrders()) {
                        if (order.getPrice() == selectedOrder.getPrice() && order.getAddress() == selectedOrder.getAddress()) {
                            selectedCustomer = customer;
                        }
                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("OrdersInfoTable.fxml"));
                    GridPane ordersInfoGrid = loader.load();
                    rootPaneOrders.setBottom(ordersInfoGrid);
                } catch (Exception e) {
                    System.out.println("ordersInfoEvent " + e);
                }
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        ordersTable.setEditable(true);
        ObservableList<TableColumn> columns = ordersTable.getColumns();

        orderIdCol.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId")); //orderId
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Order,LocalDate>("deadline")); //deadline
        statusCol.setCellValueFactory(new PropertyValueFactory<Order,OrderStatus>("status")); //status
        priceCol.setCellValueFactory(new PropertyValueFactory<Order,Double>("price")); //price

        statusCol.setCellFactory(col -> {
            return new TableCell<Order, OrderStatus>(){
                @Override
                public void updateItem(OrderStatus status, boolean empty){
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        setText(status.getName());
                    }
                }
            };
        });

        ordersTable.getColumns().setAll(orderIdCol, deadlineCol, statusCol, priceCol);
        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
        ordersTable.setOnMousePressed(ordersInfoEvent);
        ordersTable.setItems(orders);
    }
}
