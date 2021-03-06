package views.menus;

/**
 * Created by Axel Kvistad on 13.04.2016
 */
import classpackage.*;
import classpackage.Menu;
import classpackage.MenuLine;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainController;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class MenusController extends MainController implements Initializable{

    @FXML
    public GridPane subMenuGP;
    public TableView chefMenusTable;
    public TableColumn menuNameCol;
    public TableColumn dishQuantityCol;
    public TableColumn menuPriceCol;
    public Button addMenuButton;
    public Button refreshMenusButton;

    protected static Dish selectedDish;
    protected static MenuLine selectedMenuLine;
    protected static classpackage.Menu selectedMenu;

    protected static ObservableList<MealType> mealTypes = db.getAllMealTypes();
    protected static ObservableList<Supplier> supplierList = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredientList = db.getAllIngredients(supplierList);
    protected static ObservableList<Dish> dishList = db.getAllDishes(ingredientList);
    protected static ObservableList<Menu> menuList = db.getAllMenus(dishList);
    protected final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> addMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                final Stage addMenuStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("AddMenu.fxml"));
                addMenuStage.setTitle("Add new menu");
                addMenuStage.setScene(new Scene(root, 800, 800));
                addMenuStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<MouseEvent> editMenuEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                try {
                    selectedMenu = (Menu) chefMenusTable.getSelectionModel().getSelectedItem();
                    if (selectedMenu != null) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("EditMenu.fxml"));
                        GridPane editMenuGP = loader.load();
                        Scene formScene = new Scene(editMenuGP);
                        Stage formStage = new Stage();
                        formStage.setTitle("Edit menu: " + selectedMenu.getName());
                        formStage.setScene(formScene);
                        formStage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("No menu selected");
                        alert.setContentText("Select a menu to edit it.");
                        alert.showAndWait();
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };

    EventHandler<ActionEvent> refreshMenusEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                dishList = db.getAllDishes(ingredientList);
                menuList = db.getAllMenus(dishList);
                chefMenusTable.setItems(menuList);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        menuNameCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
        dishQuantityCol.setCellValueFactory(new PropertyValueFactory<Menu, ObservableList<MenuLine>>("menuLines"));
        menuPriceCol.setCellValueFactory(new PropertyValueFactory<Menu, Double>("totalPrice"));

        menuNameCol.setCellFactory(column -> {
            return new TableCell<Menu, String>() {
                @Override
                public void updateItem(String string, boolean empty) {
                    if (string == null || empty) {
                        setText(null);
                    } else {
                        setText(string);
                    }
                }
            };
        });

        dishQuantityCol.setCellFactory(column -> {
            return new TableCell<Menu, ObservableList<MenuLine>>() {
                @Override
                public void updateItem(ObservableList<MenuLine> menuLines, boolean empty) {
                    if (menuLines == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(menuLines.size()));
                    }
                }
            };
        });

        menuPriceCol.setCellFactory(column -> {
            return new TableCell<Menu, Double>() {
                @Override
                public void updateItem(Double totalPrice, boolean empty) {
                    if (totalPrice == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(totalPrice));
                    }
                }
            };
        });

        chefMenusTable.getColumns().setAll(menuNameCol, dishQuantityCol, menuPriceCol);
        chefMenusTable.setItems(menuList);
        chefMenusTable.setOnMousePressed(editMenuEvent);

        addMenuButton.setOnAction(addMenuButtonClick);
        refreshMenusButton.setOnAction(refreshMenusEvent);
    }
}
