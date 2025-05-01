# Matrix Calculator with RMI

This project implements a matrix calculator application that uses Java RMI (Remote Method Invocation) to offload matrix operations to a remote server.

## Overview

The Matrix Calculator allows users to perform various matrix operations:
- Addition
- Subtraction
- Multiplication
- Determinant calculation
- Matrix inversion
- Transpose

Instead of performing these calculations locally, the application sends the matrices to a remote server, which performs the calculations and returns the results.

## Architecture

The application follows a client-server architecture:

1. **Client**: The JavaFX application that provides the user interface for creating matrices and requesting operations.
2. **Server**: A standalone Java application that hosts the matrix operation service.
3. **RMI**: The communication mechanism that allows the client to invoke methods on the server.

## How to Run

### Starting the Server

1. First, start the RMI server using the provided batch file:
   ```
   start_server.bat
   ```

   Alternatively, you can start it directly with Java:
   ```
   java -cp <classpath> com.matrixcalculator.rmi.MatrixServer
   ```

   You should see output indicating that the server has started and is ready to accept requests:
   ```
   RMI registry created on port 1099
   MatrixOperations service bound to registry
   Server is ready to accept requests
   ```

### Starting the Client

2. After the server is running, start the client application using the provided batch file:
   ```
   start_client.bat
   ```

   Alternatively, you can start it directly with Java:
   ```
   java -cp <classpath> com.matrixcalculator.view.MatrixCalculatorApp
   ```

## Usage

1. Create Matrix A by setting the dimensions and clicking "Create Matrix"
2. Create Matrix B by setting the dimensions and clicking "Create Matrix"
3. Enter values in the matrix cells
4. Select an operation from the dropdown
5. Click "Calculate" to perform the operation
6. The result will be displayed in the result section

The status bar at the bottom of the application will show the status of the calculation and any error messages if the server is unavailable.

## Server Dependency

The application requires the remote server to be running to perform calculations. If the server is unavailable, the application will display an error message and will not be able to perform any calculations. This design ensures that all calculations are performed on the server, which can be more powerful and efficient than the client machine.

## Implementation Details

- The RMI interface `MatrixOperations` defines the operations that can be performed remotely.
- The server implementation `MatrixOperationsImpl` provides the actual implementation of these operations.
- The client controller `MatrixCalculatorController` sends all calculation requests to the remote server.

## Troubleshooting

If you encounter issues with the RMI connection:

1. Ensure the server is running before starting the client
2. Check that no firewall is blocking the RMI port (default: 1099)
3. If running on different machines, ensure the RMI_HOST in the client is set to the correct server IP address
