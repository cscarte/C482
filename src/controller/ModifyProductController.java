package controller;

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.Inventory;
import model.Part;
import model.Product;


public class ModifyProductController implements Initializable {


    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;

    @FXML
    private TextField partSearchTextField;
    @FXML
    private TextField productIDTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productInventoryTextField;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productMaxTextField;
    @FXML
    private TextField productMinTextField;

    Product productSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Initiates ModifyProductController and provides table with products in inventory system.
         *
         * @param location
         * @param resources
         */

        productSelected = MainScreenController.getProductModifying();
        associatedPart = productSelected.getAssociatedPartList();

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTableView.setItems(associatedPart);

        productIDTextField.setText(String.valueOf(productSelected.getId()));
        productNameTextField.setText(productSelected.getName());
        productInventoryTextField.setText(String.valueOf(productSelected.getStock()));
        productPriceTextField.setText(String.valueOf(productSelected.getPrice()));
        productMaxTextField.setText(String.valueOf(productSelected.getMax()));
        productMinTextField.setText(String.valueOf(productSelected.getMin()));
    }


    /**
     * Displays an alert message for the following methods:
     *
     * @param alertCase Displays one of six different alerts for ModifyProductController
     */
    private void displayAlert(int alertCase) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertCase) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Cannot Modify Product");
                alert.setContentText("Form is not filled out or contains invalid value.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Info");
                alert.setHeaderText("Part Not Found");
                alert.setContentText("Could not match part in inventory system to use as product");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Min Value");
                alert.setContentText("Min value must be equal to or greater than 0 and less than the Max value.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Stock Inventory Level");
                alert.setContentText("Inventory stock level must be between or equal to Min or Max.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("No Product Name");
                alert.setContentText("Product name cannot be blank.");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.setContentText("Must select part to proceed");
                alert.showAndWait();
                break;
        }
    }

    private void goToMainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds product to inventory management system
     * Product must be tied to part by selecting part
     * @param event Button for adding product
     */
    @FXML
    void addProductButtonMethod(ActionEvent event) {
        Part partSelected = partTableView.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            displayAlert(6);
        } else {
            associatedPart.add(partSelected);
            associatedPartTableView.setItems(associatedPart);
        }
    }

    /**
     * Displays cancel dialog to back out of adding a product. Returns to Main Screen (MainScreenController)
     *
     * */
    @FXML
    void cancelProductButtonMethod(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Button Confirmation Alert");
        alert.setContentText("Cancel changes and return to main screen?");
        Optional<ButtonType> result = alert.showAndWait(); // Used showAndWait() instead of show() as showAndWait() blocks execution until stage is closed.

        if ((result.isPresent()) && result.get() == ButtonType.OK) {
            goToMainScreen(event);
        }
    }

    /**
     * Removes part from associated parts table
     * @param event Remove button to remove part from associated parts table.
     */
    @FXML
    void removeProductButtonMethod(ActionEvent event) {
        Part partSelected = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            displayAlert(6);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Remove part selected?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPart.remove(partSelected);
                associatedPartTableView.setItems(associatedPart);
            }
        }
    }

    /**
     * Adds new product to inventory (tied to currently existing part in the inventory management system
     * Loads Main Screen (MainScreenController)
     * @param event Save button saves part to inventory management system
     * @throws IOException from FXMLLoader
     * */
    @FXML void saveProductButtonMethod(ActionEvent event) throws IOException {
        try {
            int id = productSelected.getId();
            String name = productNameTextField.getText();
            int stock = Integer.parseInt(productInventoryTextField.getText());
            Double price = Double.parseDouble(productPriceTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());

            if (name.isEmpty()) {
                displayAlert(5);
            } else {
                if (validateInventoryMinMax(min, max) && validateInventoryStock(min, max, stock)) {
                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : associatedPart) {
                        newProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.removeProduct(productSelected);
                    System.out.println("Changes to product saved!");
                    goToMainScreen(event);
                }
            }
        } catch (Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Searches based on value in the part search text field.
     * Table displays results from typed value, either by Part ID or Part Name
     * If part is not found in the inventory system, cannot add as a product
     * @param event Searches for part in inventory management system to tie to product
     */
    @FXML
    void partSearchButtonMethod(ActionEvent event) {
        String partSearchString = partSearchTextField.getText();
        ObservableList<Part> partsInInventorySystem = Inventory.getAllParts();
        ObservableList<Part> partsFoundInInventorySystem = FXCollections.observableArrayList();

        for (Part part : partsInInventorySystem) {
            if ((String.valueOf(part.getId()).contains(partSearchString)) || part.getName().contains(partSearchString)) {
                partsFoundInInventorySystem.add(part);
            }
        }
        partTableView.setItems(partsFoundInInventorySystem);
        if (partsFoundInInventorySystem.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * If search box is empty, display all parts in inventory management system
     * @param keyEvent Pressing key while part search button is clicked
     */
    @FXML void partSearchTextFieldKeyPressed(KeyEvent keyEvent) {
        if (partSearchTextField.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Validates inventory min level is greater than or equal to zero, but is less than the max inventory level allowed.
     * @param max Max inventory qty allowed to store in inventory system
     * @param min Min inventory qty allowed to store in inventory system
     * @return Boolean value for if min value is an acceptable level by being less than the max.
     */
    private boolean validateInventoryMinMax(int min, int max) {
        if (min < 0 || min > max) {
            displayAlert(3);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates inventory on hand is equal to the min or max, or between the min and max.
     * @param max Max inventory qty allowed to store in inventory system
     * @param min Min inventory qty allowed to store in inventory system
     * @param invQty The current inventory level of the part
     * @return Boolean value for if the stock level is equal to or between the max and min
     */
    private boolean validateInventoryStock (int min, int max, int invQty) {
        if (invQty >= min && invQty <= max) {
            return true;
        } else {
            displayAlert(4);
            return false;
        }
    }
}
