package com.matrixcalculator.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MatrixServer {

    private static final int PORT = 1099;

    public static final String SERVICE_NAME = "MatrixOperations";

    public static void main(String[] args) {
        try {
            MatrixOperationsImpl matrixOps = new MatrixOperationsImpl();

            Registry registry = null;
            try {
                registry = LocateRegistry.createRegistry(PORT);
                System.out.println("RMI registry created on port " + PORT);
            } catch (Exception e) {
                System.out.println("RMI registry already exists, getting reference...");
                registry = LocateRegistry.getRegistry(PORT);
            }

            registry.rebind(SERVICE_NAME, matrixOps);
            System.out.println("MatrixOperations service bound to registry");
            System.out.println("Server is ready to accept requests");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
