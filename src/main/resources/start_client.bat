@echo off
:: Matrix Calculator Client Launcher
:: Last updated: 4/15/2023

title Matrix Calculator v1.2

echo *******************************
echo *   Matrix Calculator Client  *
echo *******************************
echo.

:: TODO: Add proper error handling if Java isn't installed
:: For now this works fine on my machine

java -cp . com.matrixcalculator.view.MatrixCalculatorApp

if %ERRORLEVEL% NEQ 0 (
    echo Oops! Something went wrong :(
    echo Check if Java is installed correctly.
) else (
    echo.
    echo Client closed successfully!
)

echo.
echo Thanks for using my calculator!
timeout /t 3
