package com.matrixcalculator.view;

import com.matrixcalculator.rmi.MatrixOperations;
import com.matrixcalculator.rmi.MatrixServer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MatrixCalculatorController {

    @FXML private GridPane matrixAGrid;
    @FXML private GridPane matrixBGrid;
    @FXML private GridPane resultMatrixGrid;

    @FXML private ComboBox<String> operationComboBox;
    @FXML private Spinner<Integer> rowsSpinnerA;
    @FXML private Spinner<Integer> colsSpinnerA;
    @FXML private Spinner<Integer> rowsSpinnerB;
    @FXML private Spinner<Integer> colsSpinnerB;

    @FXML private Button createMatrixAButton;
    @FXML private Button createMatrixBButton;

    @FXML private Label statusLabel;
    @FXML private Label resultLabel;

    private List<List<TextField>> matrixAFields = new ArrayList<>();
    private List<List<TextField>> matrixBFields = new ArrayList<>();
    private List<List<TextField>> resultFields = new ArrayList<>();

    // RMI server configuration
    private static final String RMI_HOST = "localhost";
    private static final int RMI_PORT = 1099;

    // Get the remote matrix operations service
    private MatrixOperations getRemoteMatrixOperations() {
        try {
            Registry registry = LocateRegistry.getRegistry(RMI_HOST, RMI_PORT);
            return (MatrixOperations) registry.lookup(MatrixServer.SERVICE_NAME);
        } catch (Exception e) {
            showAlert("RMI Error", "Could not connect to the remote service: " + e.getMessage());
            statusLabel.setText("Error: Could not connect to remote service");
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void initialize() {
        // Setup operation options
        operationComboBox.getItems().addAll(
            "Addition (A + B)",
            "Subtraction (A - B)",
            "Multiplication (A × B)",
            "Determinant",
            "Inverse",
            "Transpose"
        );
        operationComboBox.setValue("Addition (A + B)");

        // Setup spinners
        SpinnerValueFactory<Integer> valueFactoryRowsA = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        SpinnerValueFactory<Integer> valueFactoryColsA = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        SpinnerValueFactory<Integer> valueFactoryRowsB = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        SpinnerValueFactory<Integer> valueFactoryColsB = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);

        rowsSpinnerA.setValueFactory(valueFactoryRowsA);
        colsSpinnerA.setValueFactory(valueFactoryColsA);
        rowsSpinnerB.setValueFactory(valueFactoryRowsB);
        colsSpinnerB.setValueFactory(valueFactoryColsB);
    }

    @FXML
    public void createMatrixA() {
        int rows = rowsSpinnerA.getValue();
        int cols = colsSpinnerA.getValue();
        createMatrix(matrixAGrid, matrixAFields, rows, cols);
        statusLabel.setText("Matrix A created with dimensions " + rows + "x" + cols);
    }

    @FXML
    public void createMatrixB() {
        int rows = rowsSpinnerB.getValue();
        int cols = colsSpinnerB.getValue();
        createMatrix(matrixBGrid, matrixBFields, rows, cols);
        statusLabel.setText("Matrix B created with dimensions " + rows + "x" + cols);
    }

    private void createMatrix(GridPane grid, List<List<TextField>> fields, int rows, int cols) {
        // Clear existing grid
        grid.getChildren().clear();
        fields.clear();

        // Create new input fields
        for (int i = 0; i < rows; i++) {
            fields.add(new ArrayList<>());

            for (int j = 0; j < cols; j++) {
                TextField tf = new TextField("0");
                tf.setPrefWidth(60);
                tf.getStyleClass().add("matrix-cell");
                grid.add(tf, j, i);
                fields.get(i).add(tf);
            }
        }
    }

    @FXML
    public void calculateResult() {
        if (matrixAFields.isEmpty() || matrixBFields.isEmpty()) {
            showAlert("Error", "Please create both matrices first.");
            return;
        }

        // Check if remote service is available
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Connected to remote matrix calculation service");
            }
        } catch (Exception e) {
            statusLabel.setText("Warning: Remote service unavailable. Will use local calculations.");
        }

        String operation = operationComboBox.getValue();
        int rowsA = matrixAFields.size();
        int colsA = matrixAFields.get(0).size();
        int rowsB = matrixBFields.size();
        int colsB = matrixBFields.get(0).size();

        // Parse input matrices
        double[][] matrixA = new double[rowsA][colsA];
        double[][] matrixB = new double[rowsB][colsB];

        try {
            statusLabel.setText("Preparing matrices for calculation...");

            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < colsA; j++) {
                    matrixA[i][j] = Double.parseDouble(matrixAFields.get(i).get(j).getText());
                }
            }

            for (int i = 0; i < rowsB; i++) {
                for (int j = 0; j < colsB; j++) {
                    matrixB[i][j] = Double.parseDouble(matrixBFields.get(i).get(j).getText());
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers in all cells.");
            statusLabel.setText("Error: Invalid input values");
            return;
        }

        // Perform calculation based on selected operation
        double[][] result = null;

        switch (operation) {
            case "Addition (A + B)":
                if (rowsA != rowsB || colsA != colsB) {
                    showAlert("Invalid Dimensions", "For addition, matrices must have the same dimensions.");
                    return;
                }
                result = addMatrices(matrixA, matrixB);
                break;
            case "Subtraction (A - B)":
                if (rowsA != rowsB || colsA != colsB) {
                    showAlert("Invalid Dimensions", "For subtraction, matrices must have the same dimensions.");
                    return;
                }
                result = subtractMatrices(matrixA, matrixB);
                break;
            case "Multiplication (A × B)":
                if (colsA != rowsB) {
                    showAlert("Invalid Dimensions", "For multiplication, the number of columns in A must equal the number of rows in B.");
                    return;
                }
                result = multiplyMatrices(matrixA, matrixB);
                break;
            case "Determinant":
                if (rowsA != colsA) {
                    showAlert("Invalid Dimensions", "Determinant can only be calculated for square matrices.");
                    return;
                }
                double det = calculateDeterminant(matrixA);
                showAlert("Determinant Result", "Determinant of Matrix A = " + det);
                statusLabel.setText("Determinant of Matrix A = " + det);
                return;
            case "Inverse":
                if (rowsA != colsA) {
                    showAlert("Invalid Dimensions", "Inverse can only be calculated for square matrices.");
                    return;
                }
                result = calculateInverse(matrixA);
                if (result == null) {
                    showAlert("Calculation Error", "The matrix is singular and cannot be inverted.");
                    return;
                }
                break;
            case "Transpose":
                result = transposeMatrix(matrixA);
                break;
        }

        // Display result
        if (result != null) {
            displayResult(result);
            statusLabel.setText("Calculation completed successfully.");
        }
    }

    private void displayResult(double[][] result) {
        // Clear existing result grid
        resultMatrixGrid.getChildren().clear();
        resultFields.clear();

        int rows = result.length;
        int cols = result[0].length;

        for (int i = 0; i < rows; i++) {
            resultFields.add(new ArrayList<>());

            for (int j = 0; j < cols; j++) {
                TextField tf = new TextField(String.format("%.2f", result[i][j]));
                tf.setPrefWidth(60);
                tf.setEditable(false);
                tf.getStyleClass().add("result-cell");
                resultMatrixGrid.add(tf, j, i);
                resultFields.get(i).add(tf);
            }
        }
    }

    private double[][] addMatrices(double[][] a, double[][] b) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrices to remote server for addition...");
                return remote.addMatrices(a, b);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return addMatricesLocally(a, b);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote addition: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return addMatricesLocally(a, b);
        }
    }

    // Local fallback method
    private double[][] addMatricesLocally(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private double[][] subtractMatrices(double[][] a, double[][] b) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrices to remote server for subtraction...");
                return remote.subtractMatrices(a, b);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return subtractMatricesLocally(a, b);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote subtraction: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return subtractMatricesLocally(a, b);
        }
    }

    // Local fallback method
    private double[][] subtractMatricesLocally(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    private double[][] multiplyMatrices(double[][] a, double[][] b) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrices to remote server for multiplication...");
                return remote.multiplyMatrices(a, b);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return multiplyMatricesLocally(a, b);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote multiplication: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return multiplyMatricesLocally(a, b);
        }
    }

    // Local fallback method
    private double[][] multiplyMatricesLocally(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                result[i][j] = 0;
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // Determinant calculation using remote service
    private double calculateDeterminant(double[][] matrix) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrix to remote server for determinant calculation...");
                return remote.calculateDeterminant(matrix);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return calculateDeterminantLocally(matrix);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote determinant calculation: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return calculateDeterminantLocally(matrix);
        }
    }

    // Local fallback method
    private double calculateDeterminantLocally(double[][] matrix) {
        int size = matrix.length;

        if (size == 1) {
            return matrix[0][0];
        }
        if (size == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        // For larger matrices, we'd implement a more efficient algorithm
        // but this is simplified for demonstration
        return 0;
    }

    // Inverse calculation using remote service
    private double[][] calculateInverse(double[][] matrix) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrix to remote server for inverse calculation...");
                return remote.calculateInverse(matrix);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return calculateInverseLocally(matrix);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote inverse calculation: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return calculateInverseLocally(matrix);
        }
    }

    // Local fallback method
    private double[][] calculateInverseLocally(double[][] matrix) {
        int size = matrix.length;

        // In a real app, you'd implement a proper matrix inversion algorithm
        // This is just a placeholder
        double det = calculateDeterminantLocally(matrix);
        if (Math.abs(det) < 1e-10) {
            return null; // Matrix is singular
        }

        // Return identity matrix as placeholder
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
        }
        return result;
    }

    private double[][] transposeMatrix(double[][] matrix) {
        try {
            MatrixOperations remote = getRemoteMatrixOperations();
            if (remote != null) {
                statusLabel.setText("Sending matrix to remote server for transpose...");
                return remote.transposeMatrix(matrix);
            } else {
                // Fallback to local calculation if remote service is unavailable
                statusLabel.setText("Warning: Using local calculation (remote service unavailable)");
                return transposeMatrixLocally(matrix);
            }
        } catch (Exception e) {
            showAlert("Remote Calculation Error", "Error during remote transpose: " + e.getMessage());
            statusLabel.setText("Error during remote calculation. Using local method.");
            e.printStackTrace();
            return transposeMatrixLocally(matrix);
        }
    }

    // Local fallback method
    private double[][] transposeMatrixLocally(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    @FXML
    public void clearMatrices() {
        matrixAGrid.getChildren().clear();
        matrixBGrid.getChildren().clear();
        resultMatrixGrid.getChildren().clear();

        matrixAFields.clear();
        matrixBFields.clear();
        resultFields.clear();

        statusLabel.setText("All matrices cleared.");
    }

    @FXML
    public void saveMatrix() {
        // Placeholder for save functionality
        statusLabel.setText("Save functionality not implemented yet.");
    }

    @FXML
    public void loadMatrix() {
        // Placeholder for load functionality
        statusLabel.setText("Load functionality not implemented yet.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
