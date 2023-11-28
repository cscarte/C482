package main;

/**
 * Author: Christopher Carter
 * Email: ccar491@wgu.edu
 * Student ID: 001356206
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Creates and loads main screen of inventory management system program
 */
public class Main extends Application {
    @Override
    public void start(Stage mainScreen) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Scene scene = new Scene(root);
        mainScreen.setTitle("Inventory System - Main Screen");
        mainScreen.setScene(scene);
        mainScreen.show();
    }
}
