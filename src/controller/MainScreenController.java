package controller;

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */



import model.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import model.Inventory;
import model.Part;
import model.Product;
import model.InHouse;
import model.Outsourced;

import javax.swing.*;

import static javafx.fxml.FXMLLoader.*;
import static model.Inventory.*;

/**
 * Populates Main Screen of Inventory Management system from MainScreen.FXML
 * */
public class MainScreenController implements Initializable {

    @FXML private TableColumn<Part, Integer> partsPartIDColumn;
    @FXML private TableColumn<Part, String> partsPartNameColumn;
    @FXML private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partsPricePerUnitColumn;
    @FXML private TextField queryPartTextField;
    @FXML private TableView<Part> partsTable;

    @FXML private TableColumn<Product, Integer> productsProductIDColumn;
    @FXML private TableColumn<Product, String> productsProductNameColumn;
    @FXML private TableColumn<Product, Integer> productsInventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productsPricePerUnitColumn;
    @FXML private TextField queryProductTextField;
    @FXML private TableView<Product> productsTable;


    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static Part partModifying;
    private static Product productModifying;

    public static Part getPartModifying() {
        return partModifying;
    }

    public static Product getProductModifying() {
        return productModifying;
    }

    private static boolean firstTime = true;

    private void addTestData() {
        if(!firstTime){
            return;
        }
        firstTime = false;
        InHouse food = new InHouse(getNewPartID(), "Snickers", 1.99, 2, 0, 10, 1);
        InHouse food2 = new InHouse(getNewPartID(), "Doritos", 2.99, 7, 0, 100, 2);
        Outsourced food3 = new Outsourced(getNewPartID(), "Slim Jim", 3.99, 20, 0, 100, "Congara Brands");
        Outsourced drink = new Outsourced(getNewPartID(), "Mountain Dew", 1.99, 30, 0, 200, "PepsiCo");

        Inventory.addPart(food);
        Inventory.addPart(food2);
        Inventory.addPart(food3);
        Inventory.addPart(drink);

        Product totally_nutritious_gamer_snack_pack = new Product(getNewProductID(), "Totally Nutritious Gamer Snack Pack", 5, 0, 1, 10);
        Inventory.addProduct(totally_nutritious_gamer_snack_pack);
        totally_nutritious_gamer_snack_pack.addAssociatedPart(food2);
        totally_nutritious_gamer_snack_pack.addAssociatedPart(drink);
        totally_nutritious_gamer_snack_pack.setStock(5);

    }

    /**
     * Creates table to display data on the main screen in the program. Data is pulled from Parts and Products screens.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();

        partsTable.setItems(getAllParts());
        partsPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(getAllProducts());
        productsProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Exits inventory management system program.
     * @param actionEvent Closes program
     * */
    public void onMainExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** Displays an alert message for the following methods:
     * partSaveButton, validateInventoryMinMax, validateInventoryStock
     * @param alertCase Displays one of five different alerts for ModifyPartController
     */
    private void displayAlert(int alertCase) {
        Alert alertE = new Alert(Alert.AlertType.ERROR);
        Alert alertI = new Alert(Alert.AlertType.INFORMATION);

        switch (alertCase) {
            case 1:
                alertI.setTitle("Information");
                alertI.setHeaderText("Part not in system");
                alertI.showAndWait();
                break;
            case 2:
                alertI.setTitle("Information");
                alertI.setHeaderText("No part selected");
                alertI.showAndWait();
                break;
            case 3:
                alertE.setTitle("Error");
                alertE.setHeaderText("Product not in system");
                alertE.showAndWait();
                break;
            case 4:
                alertE.setTitle("Error");
                alertE.setHeaderText("No product selected");
                alertE.showAndWait();
                break;
            case 5:
                alertE.setTitle("Error");
                alertE.setHeaderText("Product associated with Part");
                alertE.setContentText("Parts must be decoupled from product before removing from system");
                alertE.showAndWait();
                break;
        }
    }

    /**
     * Opens the Add Part screen for the user to add a part to the inventory system.
     * @param actionEvent Opens the Add Part screen
     * @throws IOException from FXMLLoader
     * */
    @FXML void addPart(ActionEvent actionEvent) throws IOException {
        System.out.println("Opening 'Add Part' screen!");

        Parent parent;
        parent = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Opens ModifyPart.fxml
     * @param event Opens ModifyPart.fxml to display the modify part screen
     * */
    @FXML void modifyPart(ActionEvent event) throws IOException {
        System.out.println("Modify parts button clicked!");
        partModifying = partsTable.getSelectionModel().getSelectedItem();

        if (partModifying == null) {
            displayAlert(2);
        } else {
            Parent parent = load(getClass().getResource("/view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete part rom partsTable
     * @param event Remove part from inventory
     */
    @FXML void deletePart(ActionEvent event) {
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            displayAlert(2);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete part selected?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.removePart(partSelected);
            }
        }
    }

    /**
     * Search for part ID or part name currently in inventory system
     * @param event Text field to search for part in inventory system by its ID or Name.
     */
    @FXML void queryPartFunction(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        String partQueryString = queryPartTextField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(partQueryString) || part.getName().contains(partQueryString)) {
                foundPart.add(part);
            }
        }

        partsTable.setItems(foundPart);
        if (foundPart.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * Populates table with all items if search box text field is empty
     * @param event Check if part search text box is empty
     */
    @FXML void partQueryKeyPressed(KeyEvent event) {
        if (queryPartTextField.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Opens the Add Product screen for the user to add a product to the inventory system, tying it to a part.
     * @param event Opens the Add Product screen
     * @throws IOException from FXMLLoader
     */
    @FXML void addProduct(ActionEvent event) throws IOException {
        System.out.println("Opening 'Add Product' screen!");

        Parent parent = load(getClass().getResource("/view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens ModifyProduct.fxml
     * @param event Opens ModifyProduct.fxml to display the modify product screen
     * */
    @FXML void modifyProduct(ActionEvent event) throws IOException {
        System.out.println("Modify product button clicked!");
        productModifying = productsTable.getSelectionModel().getSelectedItem();

        if (productModifying == null) {
            displayAlert(4);
        } else {
            Parent parent = load(getClass().getResource("/view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete product from the product table in inventory system.
     * @param event Delete product from productsTable
     */
    @FXML void deleteProduct(ActionEvent event) {
        Product productSelected = productsTable.getSelectionModel().getSelectedItem();

        if (productSelected == null) {
            displayAlert(4);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete product selected?");
            Optional<ButtonType> result = alert.showAndWait();

            /**
             * Added productSelected.getAssociatedPartList().isEmpty() to check if product has any associated parts in the system
             * Cannot delete until product has zero associated parts.
             * */
            if (result.isPresent() && productSelected.getAssociatedPartList().isEmpty() && result.get() == ButtonType.OK){
                Inventory.removeProduct(productSelected);
            }

            ObservableList<Part> partsAssociated = productSelected.getAssociatedPartList();
            if (partsAssociated.size() >= 1) {
                displayAlert(5);
            }
        }
    }

    /**
     * Search for product ID or product name currently in inventory system
     * @param event Text field to search for product in inventory system by its ID or Name.
     */
    @FXML void queryProductFunction(ActionEvent event) {
        ObservableList<Product> allProducts = getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        String productQueryString = queryProductTextField.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(productQueryString) || product.getName().contains(productQueryString)) {
                foundProduct.add(product);
            }
        }

        productsTable.setItems(foundProduct);
        if (foundProduct.size() == 0) {
            displayAlert(3);
        }
    }

    /**
     * Populates table with all items if search box text field is empty
     * @param keyEvent Check if product search text box is empty
     */
    @FXML void productQueryKeyPressed(KeyEvent keyEvent) {
        if (queryProductTextField.getText().isEmpty()) {
            productsTable.setItems(Inventory.getAllProducts());
        }
    }
}