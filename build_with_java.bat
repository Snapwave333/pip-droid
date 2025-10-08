@echo off
echo Setting up Java environment and building Pip-Boy project...
echo =========================================================

echo Setting JAVA_HOME...
set JAVA_HOME=C:\Program Files\Java\jdk-17
echo JAVA_HOME set to: %JAVA_HOME%

echo Adding Java to PATH...
set PATH=%JAVA_HOME%\bin;%PATH%
echo Java bin directory added to PATH

echo Verifying Java installation...
java -version
if errorlevel 1 (
    echo ERROR: Java installation not found or not working properly
    pause
    exit /b 1
)

echo.
echo Java environment setup completed successfully!
echo.

echo Running Gradle build...
echo ====================
call gradlew build --no-daemon

if errorlevel 1 (
    echo.
    echo Build failed!
    pause
    exit /b 1
) else (
    echo.
    echo Build completed successfully!
    pause
)
