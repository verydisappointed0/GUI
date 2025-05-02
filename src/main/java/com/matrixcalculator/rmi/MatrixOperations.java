package com.matrixcalculator.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for remote matrix operations.
 * 
 * This defines the operations our server will provide.
 * All methods throw RemoteException because that's how RMI works.
 * 
 * @author John
 * @version 1.2
 */
public interface MatrixOperations extends Remote {

    /**
     * Adds two matrices together
     * 
     * @param a First matrix
     * @param b Second matrix
     * @return The sum matrix
     * @throws RemoteException if RMI stuff breaks
     */
    double[][] addMatrices(double[][] a, double[][] b) throws RemoteException;

    /**
     * Subtracts matrix b from matrix a
     * 
     * @param a Matrix to subtract from
     * @param b Matrix to subtract
     * @return Difference of matrices
     * @throws RemoteException if network fails
     */
    double[][] subtractMatrices(double[][] a, double[][] b) throws RemoteException;

    /**
     * Matrix multiplication (not element-wise!)
     * 
     * @param a First matrix
     * @param b Second matrix
     * @return Product matrix
     * @throws RemoteException if server dies
     */
    double[][] multiplyMatrices(double[][] a, double[][] b) throws RemoteException;

    /**
     * Finds the determinant
     * Note: Only works for 1x1 and 2x2 matrices right now!
     * 
     * @param matrix Square matrix to find determinant for
     * @return the determinant value
     * @throws RemoteException on communication error
     */
    double calculateDeterminant(double[][] matrix) throws RemoteException;

    /**
     * Calculates matrix inverse
     * 
     * @param matrix Matrix to invert (must be square)
     * @return Inverted matrix or null if not invertible
     * @throws RemoteException when RMI fails
     */
    double[][] calculateInverse(double[][] matrix) throws RemoteException;

    /**
     * Flips the matrix along its diagonal
     * 
     * @param matrix Matrix to transpose
     * @return Transposed matrix
     * @throws RemoteException on server error
     */
    double[][] transposeMatrix(double[][] matrix) throws RemoteException;
}
