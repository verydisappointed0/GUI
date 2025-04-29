package com.matrixcalculator.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the MatrixOperations remote interface.
 * This class provides the actual implementation of matrix operations
 * that will be executed on the server.
 */
public class MatrixOperationsImpl extends UnicastRemoteObject implements MatrixOperations {
    
    /**
     * Constructor.
     * 
     * @throws RemoteException If a remote communication error occurs
     */
    public MatrixOperationsImpl() throws RemoteException {
        super();
    }
    
    @Override
    public double[][] addMatrices(double[][] a, double[][] b) throws RemoteException {
        System.out.println("Server: Performing matrix addition");
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
    
    @Override
    public double[][] subtractMatrices(double[][] a, double[][] b) throws RemoteException {
        System.out.println("Server: Performing matrix subtraction");
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
    
    @Override
    public double[][] multiplyMatrices(double[][] a, double[][] b) throws RemoteException {
        System.out.println("Server: Performing matrix multiplication");
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
    
    @Override
    public double calculateDeterminant(double[][] matrix) throws RemoteException {
        System.out.println("Server: Calculating determinant");
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
    
    @Override
    public double[][] calculateInverse(double[][] matrix) throws RemoteException {
        System.out.println("Server: Calculating matrix inverse");
        int size = matrix.length;
        
        // In a real app, you'd implement a proper matrix inversion algorithm
        // This is just a placeholder
        double det = calculateDeterminant(matrix);
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
    
    @Override
    public double[][] transposeMatrix(double[][] matrix) throws RemoteException {
        System.out.println("Server: Transposing matrix");
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
}