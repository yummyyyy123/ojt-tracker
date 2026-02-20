@echo off
echo ========================================
echo OJT Tracker APK Build Script
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo.
    echo Please install JDK 11+ and set JAVA_HOME environment variable
    echo.
    echo Download Java from: https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo Java version check passed...
echo.

REM Clean previous builds
echo Cleaning previous builds...
call gradlew.bat clean >nul 2>&1

REM Build the APK
echo Building APK (this may take several minutes)...
echo.
call gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo SUCCESS: APK built successfully!
    echo ========================================
    echo.
    echo APK Location: app\build\outputs\apk\debug\app-debug.apk
    echo File Size: 
    dir "app\build\outputs\apk\debug\app-debug.apk" | findstr app-debug.apk
    echo.
    echo You can now install this APK on your Android device
    echo.
    echo To install via ADB (if connected):
    echo adb install app\build\outputs\apk\debug\app-debug.apk
    echo.
) else (
    echo.
    echo ========================================
    echo ERROR: Build failed!
    echo ========================================
    echo.
    echo Please check the error messages above
    echo Common issues:
    echo - Missing Android SDK
    echo - Network connectivity issues
    echo - Insufficient disk space
    echo.
    echo For detailed troubleshooting, see BUILD_INSTRUCTIONS.md
    echo.
)

pause
