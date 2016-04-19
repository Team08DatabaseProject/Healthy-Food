package users.chef;

import classpackage.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Created by axelkvistad on 4/18/16.
 */
public class ControllerChefEditDish extends ControllerChef implements Initializable {

    public GridPane editDishGP;
    public TextField editDishNameField;
    public TextField editDishPriceFactorField;
    public ComboBox<DishLine> editIngredientCB;
    public Button applyDishChangesButton;
    public Button addIngButton;
    public Button removeIngButton;
    public TableView currentIngTable;
    public TableColumn currentIngName;
    public TableColumn currentIngAmount;
    public TableColumn currentIngUnit;
    public TableColumn currentIngPrice;
    public Button editDishNameButton;
    public Button editPriceButton;
    public Label currentNameLabel;
    public Label currentPriceLabel;
    public Button editDishCommitButton;
    protected DishLine selectedDishLine;

    private String dishNameString;
    private double dishPriceFactor;
    private String dishPriceFactorString;
    private double dishPrice = 0;

    // Current DishLines (ingredients) in the dish being edited
    ObservableList<DishLine> currentDishLines = selectedDish.getAllDishLinesForThisDish();


    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> commitChanges = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String name = editDishNameField.getText();
                String priceFactorString = editDishPriceFactorField.getText();
                double priceFactor = Double.parseDouble(priceFactorString) / 100;
                double price = 0;
                for (DishLine dl : currentDishLines) {
                    price += (dl.getAmount() * dl.getIngredient().getPrice());
                }
                price *= priceFactor;
                double finalPrice = Math.round(price * 100.0) / 100.0;

                selectedDish.setDishName(name);
                selectedDish.setPrice(finalPrice);
                selectedDish.setAllDishLinesForThisDish(currentDishLines);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> applyChanges = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!(editDishNameField.getText().isEmpty() || editDishPriceFactorField.getText().isEmpty() || currentDishLines.isEmpty())) {
                    dishNameString = editDishNameField.getText();;
                    dishPriceFactorString = editDishPriceFactorField.getText();
                    dishPriceFactor = Double.parseDouble(dishPriceFactorString) / 100.0;
                    dishPrice = 0;
                    for (DishLine dl : currentDishLines) {
                        dishPrice += (dl.getAmount() * dl.getIngredient().getPrice());
                    }
                    dishPrice *= dishPriceFactor;
                    dishPrice = Math.round(dishPrice * 100.0) / 100.0;
                    String dishPriceString = nf.format(dishPrice);

                    currentNameLabel.setText("Dish name: " + dishNameString);
                    currentPriceLabel.setText("Dish price: " + dishPriceString + " NOK");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    EventHandler<ActionEvent> removeFromDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean remove = false;
                if (selectedDishLine != null) {
                    for (DishLine dl : currentDishLines) {
                        if (dl.getIngredient().getIngredientName().equals(selectedDishLine.getIngredient().getIngredientName())) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        currentDishLines.remove(selectedDishLine);
                        currentIngTable.setItems(currentDishLines);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ingredient cannot be removed");
                        alert.setContentText("The selected ingredient cannot be removed from the dish,\nas the dish does not contain it.");
                        alert.showAndWait();
                    }
                }
            } catch(Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> addToDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                if (selectedDishLine != null) {
                    for (DishLine dl : currentDishLines) {
                        if (dl.getIngredient().getIngredientName().equals(selectedDishLine.getIngredient().getIngredientName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        currentDishLines.add(selectedDishLine);
                        currentIngTable.setItems(currentDishLines);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ingredient cannot be added");
                        alert.setContentText("This ingredient cannot be added to the dish,\nas the dish already contains it.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public double getDishPriceFactor() {
        double ingPriceTotal = 0;
        for (DishLine dl : selectedDish.getAllDishLinesForThisDish()) {
            ingPriceTotal += dl.getIngredient().getPrice() * dl.getAmount();
        }
        System.out.println(selectedDish.getPrice() / ingPriceTotal);
        return Math.round((selectedDish.getPrice() / ingPriceTotal * 100.0));
    }


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        editIngredientCB.setItems(testDishLines);
        editDishNameField.setText(selectedDish.getDishName());
        editDishPriceFactorField.setText(nf.format(getDishPriceFactor()));
        currentNameLabel.setText("Dish name: " + selectedDish.getDishName());
        currentPriceLabel.setText("Price: " + selectedDish.getPrice());

        editIngredientCB.setConverter(new StringConverter<DishLine>() {
            @Override
            public String toString(DishLine dishLine) {
                if (dishLine == null) {
                    return null;
                } else {
                    return dishLine.getIngredient().getIngredientName();
                }
            }
            @Override
            public DishLine fromString(String string) {
                return null;
            }
        });

        editIngredientCB.valueProperty().addListener(new ChangeListener<DishLine>() {
            @Override
            public void changed(ObservableValue<? extends DishLine> observable, DishLine oldValue, DishLine newValue) {
                selectedDishLine = newValue;
            }
        });

        currentIngName.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        currentIngAmount.setCellValueFactory(new PropertyValueFactory<DishLine, Double>("amount"));
        currentIngUnit.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        currentIngPrice.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));


        currentIngName.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(ingredient.getIngredientName());
                    }
                }
            };
        });

        currentIngAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        currentIngAmount.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<DishLine, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<DishLine, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                        // = event.getNewValue();
                    }
                });

        currentIngPrice.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(ingredient.getPrice()));
                    }
                }
            };
        });

        /*

        chosenIngPrice.setCellFactory(lv -> {
            TableCell<DishLine, Ingredient> cell = new TableCell<>();
            cell.itemProperty().addListener(new ChangeListener<Ingredient>() {
                @Override
                public void changed(ObservableValue<? extends Ingredient> observable, Ingredient oldValue, Ingredient newValue) {
                    if (newValue != null) {
                        cell.setText(String.valueOf(newValue.getPrice() * ));
                    }
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return cell;
        });

*/
        currentIngUnit.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(ingredient.getUnit());
                    }
                }
            };
        });


        currentIngTable.getColumns().setAll(currentIngName, currentIngAmount, currentIngUnit, currentIngPrice);
        currentIngTable.setEditable(true);
        currentIngTable.setItems(currentDishLines);

        addIngButton.setOnAction(addToDishButtonClick);
        removeIngButton.setOnAction(removeFromDishButtonClick);
        editDishCommitButton.setOnAction(commitChanges);
        applyDishChangesButton.setOnAction(applyChanges);

    }
}
