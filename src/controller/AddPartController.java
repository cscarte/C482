package controller;

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * Creates and opens screen to add parts to inventory system. Can choose if part is made in house or outsourced here.
 * @author Christopher Carter
 */
public class AddPartController implements Initializable {
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private ToggleGroup radioButtonPartTypeGroup; // Toggles between In House and Outsourced on the Add Part Screen

    @FXML Label partMachineIDLabel; // The label containing the Int machine ID or String company name for a part.
    @FXML private TextField partIDTextField;
    @FXML private TextField partMachineIDorCompanyName; // The text field containing the Int machine ID or String company name for a part.
    @FXML private TextField partNameTextField;
    @FXML private TextField partInventoryLevelTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxInventoryTextField;
    @FXML private TextField partMinInventoryTextField;

    /**
     * Sets partIDNameLabel to "Machine ID". This is for parts made in house.
     * @param event Selects the in house radio button
     */
    @FXML void inHouseRadioButtonSelected(ActionEvent event) {
        partMachineIDLabel.setText("Machine ID");
    }

    /***
     * Sets partIDNameLabel to "Company Name". This is for parts outsourced.
     * @param event Selects the outsourced radio button
     */
    @FXML void outsourcedRadioButtonSelected(ActionEvent event) {
        partMachineIDLabel.setText("Company Name");
    }

    /**
     * Sets part to In House or Outsourced via two radio buttons. Defaults to In House option for part.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inHouseRadioButton.setSelected(true);
    }

    /** Displays an alert message for the following methods:
     * partSaveButton, validateInventoryMinMax, validateInventoryStock
     * Used cases to for easier viewing of each alert than using multiple 'if' statements
     * @param alertCase Displays one of five different alerts for AddPartController
     */
    private void displayAlert(int alertCase) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertCase) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Cannot Add Part");
                alert.setContentText("Form is not filled out or contains invalid value.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value Type for Machine ID");
                alert.setContentText("Machine ID must be an integer.");
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
                alert.setHeaderText("No Part Name");
                alert.setContentText("Part name cannot be blank.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Displays cancel confirmation dialog and returns to Main Screen if accepted.
     * @param event Cancel button
     * @throws IOException from FXMLLoader
     */
    @FXML void partCancelButtonMethod(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Button Confirmation Alert");
        alert.setContentText("Cancel changes and return to main screen?");
        Optional<ButtonType> result = alert.showAndWait(); // Used showAndWait() instead of show() as showAndWait() blocks execution until stage is closed.

        if ((result.isPresent()) && result.get() == ButtonType.OK) {
            goToMainScreen(event);
        }
    }

    /**
     * This method allows you to save a part to the system.
     * Choose between in house made or outsourced.
     * @param event Save button
     * @throws IOException from FXMLLoader
     */
    @FXML void addPartSaveButtonMethod(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = partNameTextField.getText();
            Double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInventoryLevelTextField.getText());
            int max = Integer.parseInt(partMaxInventoryTextField.getText());
            int min = Integer.parseInt(partMinInventoryTextField.getText());
            int machineID;
            String companyName;
            boolean partAdded = false;

            if (name.isEmpty()) {
                displayAlert(5);
            } else {
                if (validateInventoryMinMax(min, max) && validateInventoryStock(min, max, stock)) {
                    if (inHouseRadioButton.isSelected()) {
                        try {
                            machineID = Integer.parseInt(partMachineIDorCompanyName.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                            newInHousePart.setId(Inventory.getNewPartID());
                            Inventory.addPart(newInHousePart);
                            partAdded = true;
                        } catch (Exception e) {
                            displayAlert(2);
                        }
                    }

                    if (outsourcedRadioButton.isSelected()) {
                        companyName = partMachineIDorCompanyName.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartID());
                        Inventory.addPart(newOutsourcedPart);
                        partAdded = true;
                    }

                    if (partAdded) {
                        System.out.println("Part added!");
                        goToMainScreen(event);
                    }
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }


    /***
     * Loads Main Screen (mainScreenController)
     * @param event Returns to the main screen
     * @throws IOException from FXMLLoader
     */
    private void goToMainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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