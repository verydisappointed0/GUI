package com.matrixcalculator.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for matrix operations.
 * This interface defines all matrix operations that can be performed remotely.
 */
public interface MatrixOperations extends Remote {
    
    /**
     * Adds two matrices.
     * 
     * @param a First matrix
     * @param b Second matrix
     * @return Result of addition
     * @throws RemoteException If a remote communication error occurs
     */
    double[][] addMatrices(double[][] a, double[][] b) throws RemoteException;
    
    /**
     * Subtracts second matrix from the first.
     * 
     * @param a First matrix
     * @param b Second matrix
     * @return Result of subtraction
     * @throws RemoteException If a remote communication error occurs
     */
    double[][] subtractMatrices(double[][] a, double[][] b) throws RemoteException;
    
    /**
     * Multiplies two matrices.
     * 
     * @param a First matrix
     * @param b Second matrix
     * @return Result of multiplication
     * @throws RemoteException If a remote communication error occurs
     */
    double[][] multiplyMatrices(double[][] a, double[][] b) throws RemoteException;
    
    /**
     * Calculates the determinant of a matrix.
     * 
     * @param matrix Input matrix
     * @return Determinant value
     * @throws RemoteException If a remote communication error occurs
     */
    double calculateDeterminant(double[][] matrix) throws RemoteException;
    
    /**
     * Calculates the inverse of a matrix.
     * 
     * @param matrix Input matrix
     * @return Inverse matrix or null if matrix is singular
     * @throws RemoteException If a remote communication error occurs
     */
    double[][] calculateInverse(double[][] matrix) throws RemoteException;
    
    /**
     * Transposes a matrix.
     * 
     * @param matrix Input matrix
     * @return Transposed matrix
     * @throws RemoteException If a remote communication error occurs
     */
    double[][] transposeMatrix(double[][] matrix) throws RemoteException;
}