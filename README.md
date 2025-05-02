# Matrix Calculator with RMI

Hey there! This is my matrix calculator project that I built for my OOP class. It uses Java RMI to do all the heavy lifting on a separate server - pretty cool, right?

## What it does

This calculator lets you play around with matrices and do stuff like:
- Adding matrices together
- Subtracting one matrix from another
- Matrix multiplication (the tricky one!)
- Finding determinants
- Inverting matrices (when possible)
- Transposing matrices

The neat part is that instead of making your poor laptop do all the work, it sends everything to a server that handles the calculations and sends back the results.

## How it's built

I went with a client-server setup:

1. **Client**: A JavaFX app with a (hopefully) nice UI where you can create matrices and click buttons
2. **Server**: A separate Java program that sits there waiting to do math for you
3. **RMI**: The magic that lets them talk to each other

## Getting it running

### Fire up the server first

1. The easiest way is to just double-click the batch file:
   ```
   start_server.bat
   ```

   Or if you're a command line person:
   ```
   java -cp <classpath> com.matrixcalculator.rmi.MatrixServer
   ```

   You should see something like:
   ```
   RMI registry created on port 1099
   MatrixOperations service bound to registry
   Server is ready to accept requests
   ```

### Then start the client

2. Once the server's running, start the actual calculator:
   ```
   start_client.bat
   ```

   Or manually:
   ```
   java -cp <classpath> com.matrixcalculator.view.MatrixCalculatorApp
   ```

## How to use it

1. Set up Matrix A by picking dimensions and hitting "Create Matrix"
2. Do the same for Matrix B
3. Type in your values (or just use the defaults)
4. Pick an operation from the dropdown
5. Hit "Calculate" and watch the magic happen
6. Your answer appears in the result area!

There's a status bar at the bottom that'll let you know if something goes wrong.

## Important note!

The calculator NEEDS the server running to work! If the server's not running, you'll get error messages and nothing will calculate. I designed it this way on purpose to practice with RMI.

## Under the hood

- I created an interface called `MatrixOperations` that defines all the operations
- The server implements these operations in `MatrixOperationsImpl`
- The client's `MatrixCalculatorController` handles the UI and talks to the server

## Troubleshooting

If things aren't working:

1. Make sure you started the server BEFORE the client
2. Check if your firewall is blocking port 1099
3. If you're trying to run this across different computers, you'll need to change the RMI_HOST in the client code

Let me know if you find any bugs! This was a fun project to build.
