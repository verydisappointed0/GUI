package com.matrixcalculator.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixOperations extends Remote {

    double[][] addMatrices(double[][] a, double[][] b) throws RemoteException;

    double[][] subtractMatrices(double[][] a, double[][] b) throws RemoteException;

    double[][] multiplyMatrices(double[][] a, double[][] b) throws RemoteException;

    double calculateDeterminant(double[][] matrix) throws RemoteException;

    double[][] calculateInverse(double[][] matrix) throws RemoteException;

    double[][] transposeMatrix(double[][] matrix) throws RemoteException;
}
