@echo off
echo Compiling Java source files...
if not exist bin mkdir bin
javac -d bin src/*.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    exit /b %ERRORLEVEL%
)
echo Running LibraryManagementTest test suite...
java -cp bin LibraryManagementTest
