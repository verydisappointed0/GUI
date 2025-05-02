package com.matrixcalculator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MatrixCalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/matrixcalculator/view/matrix_calculator.fxml")));

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(Objects.requireNonNull(
            getClass().getResource("/com/matrixcalculator/view/styles.css")).toExternalForm());

        primaryStage.setTitle("Matrix Calculator v1.2");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
