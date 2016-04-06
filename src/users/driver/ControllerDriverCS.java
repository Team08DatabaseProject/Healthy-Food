package users.driver;

/**
 * Created by axelkvistad on 3/24/16.
 * ControllerDriverChangeStatus.java
 */
import classpackage.Order;
import classpackage.SqlQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

// // TODO: 3/27/16 make class work similar to ControllerDriver.java. Also make method for changing status communicate with database

public class ControllerDriverCS implements Initializable {

    private SqlQueries query = new SqlQueries();

    public GridPane centerGrid;
    public TableView tables;
    public Button deliveredButton;
    DriverOrderStatus d1 = new DriverOrderStatus(1, "Testveien 1", "13:00", "Not delivered");
    DriverOrderStatus d2 = new DriverOrderStatus(2, "Testveien 2", "14:00", "Not delivered");
    DriverOrderStatus d3 = new DriverOrderStatus(3, "Testveien 3", "15:00", "Not delivered");
    DriverOrderStatus d4 = new DriverOrderStatus(4, "Testveien 4", "16:00", "Not delivered");

    final ObservableList<DriverOrderStatus> changeStatusData = FXCollections.observableArrayList(d1, d2, d3, d4);
    final ObservableList<Order> orderList = query.getOrders(1); // Fucked up, fix

    EventHandler<ActionEvent> markAsDelivered = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                ((DriverOrderStatus) tables.getSelectionModel().getSelectedItem()).setStatus("Delivered");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ObservableList<TableColumn> columns = tables.getColumns();
        d1.setViktigInfo("abc");

        columns.get(0).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,Integer>("orderNo"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("address"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("deadline"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("status"));
        tables.setItems(changeStatusData);
        deliveredButton.setOnAction(markAsDelivered);
    }
}