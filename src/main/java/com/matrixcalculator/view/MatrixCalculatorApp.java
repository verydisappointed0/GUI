package com.matrixcalculator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Main application class for the Matrix Calculator
 * Created by John for OOP2 class project
 * Last updated: April 15, 2023
 */
public class MatrixCalculatorApp extends Application {

    // TODO: Add support for saving/loading matrices from files
    // TODO: Maybe add more matrix operations in the future?

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main FXML - had some issues with this path at first!
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/matrixcalculator/view/matrix_calculator.fxml")));

        // Create scene - 900x700 seems to work well on my laptop
        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/com/matrixcalculator/view/styles.css")).toExternalForm());

        // Set up the window
        primaryStage.setTitle("Matrix Calculator v1.2");
        primaryStage.setScene(scene);

        // Make sure the window isn't too small - UI gets weird otherwise
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.show();
    }

    public static void main(String[] args) {
        // Let's go!
        launch(args);
    }
}
