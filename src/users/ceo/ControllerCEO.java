package users.ceo;
/**
 * Created by Axel 16.03.2016
 * Controller for the CEO
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import classpackage.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCEO implements Initializable {

    @FXML
    //public TableView tables2; // Retrieves TableView with fx:id="tables2"
    public Button employeesButton;
    public BorderPane rootPaneDriver;
    protected SqlQueries db = new SqlQueries();

    EventHandler<ActionEvent> viewEmployees = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CEOEmployees.fxml"));
                GridPane employeesTable = loader.load();
                rootPaneDriver.setCenter(employeesTable);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
        employeesButton.setOnAction(viewEmployees);
    }
}
