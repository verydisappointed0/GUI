package com.matrixcalculator.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server application that hosts the remote matrix operations service.
 * This class starts the RMI registry and binds the MatrixOperationsImpl object to it.
 */
public class MatrixServer {
    
    // The port on which the RMI registry will run
    private static final int PORT = 1099;
    
    // The name under which the remote object will be bound in the registry
    public static final String SERVICE_NAME = "MatrixOperations";
    
    public static void main(String[] args) {
        try {
            // Create and export the remote object
            MatrixOperationsImpl matrixOps = new MatrixOperationsImpl();
            
            // Create or get the registry
            Registry registry = null;
            try {
                // Try to create a new registry
                registry = LocateRegistry.createRegistry(PORT);
                System.out.println("RMI registry created on port " + PORT);
            } catch (Exception e) {
                // If creation fails, try to get the existing registry
                System.out.println("RMI registry already exists, getting reference...");
                registry = LocateRegistry.getRegistry(PORT);
            }
            
            // Bind the remote object to the registry
            registry.rebind(SERVICE_NAME, matrixOps);
            System.out.println("MatrixOperations service bound to registry");
            System.out.println("Server is ready to accept requests");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}