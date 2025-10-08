@echo off
echo Setting up Java environment for Android development...
echo ===================================================

echo Setting JAVA_HOME...
set JAVA_HOME=C:\Program Files\Java\jdk-17
echo JAVA_HOME set to: %JAVA_HOME%

echo Adding Java to PATH...
set PATH=%JAVA_HOME%\bin;%PATH%
echo Java bin directory added to PATH

echo Verifying Java installation...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java installation not found or not working properly
    pause
    exit /b 1
)

echo Java environment setup completed successfully!
echo You can now run Gradle builds.
pause
