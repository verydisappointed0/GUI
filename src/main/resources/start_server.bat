@echo off
REM Matrix Server Launcher
REM Created by John - April 2023
REM Make sure Java is in your PATH!

echo ===================================
echo Starting Matrix Calculator Server...
echo ===================================

REM Sometimes the classpath needs tweaking depending on where you run this from
java -cp . com.matrixcalculator.rmi.MatrixServer

echo.
echo Server stopped. Press any key to close this window.
pause > nul
