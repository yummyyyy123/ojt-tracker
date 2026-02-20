@echo off
echo ========================================
echo OJT Tracker - Java Setup and Build
echo ========================================
echo.

echo Step 1: Checking Java installation...
echo.

REM Try to find Java in common locations
set JAVA_FOUND=0

REM Check Program Files
if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-17
    set JAVA_FOUND=1
    echo Found JDK 17 in C:\Program Files\Java\jdk-17
)

if exist "C:\Program Files\Java\jdk-11\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-11
    set JAVA_FOUND=1
    echo Found JDK 11 in C:\Program Files\Java\jdk-11
)

REM Check Adoptium
if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.*-hotspot\bin\java.exe" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17.*-hotspot") do (
        set JAVA_HOME=%%i
        set JAVA_FOUND=1
        echo Found Adoptium JDK in %%i
        goto :found_java
    )
)

REM Check Program Files (x86)
if exist "C:\Program Files (x86)\Java\jdk-17\bin\java.exe" (
    set JAVA_HOME=C:\Program Files (x86)\Java\jdk-17
    set JAVA_FOUND=1
    echo Found JDK 17 in C:\Program Files (x86)\Java\jdk-17
)

:found_java
if %JAVA_FOUND%==1 (
    echo.
    echo Setting JAVA_HOME to: %JAVA_HOME%
    set PATH=%JAVA_HOME%\bin;%PATH%
    
    echo.
    echo Testing Java installation...
    "%JAVA_HOME%\bin\java.exe" -version
    if %errorlevel% equ 0 (
        echo SUCCESS: Java is working!
        echo.
        echo Step 2: Building APK...
        echo.
        call gradlew.bat assembleDebug
        
        if %errorlevel% equ 0 (
            echo.
            echo ========================================
            echo BUILD SUCCESSFUL!
            echo ========================================
            echo.
            echo APK Location: app\build\outputs\apk\debug\app-debug.apk
            echo.
            echo You can now install this APK on your Android device
            echo.
            dir "app\build\outputs\apk\debug\app-debug.apk"
        ) else (
            echo.
            echo ========================================
            echo BUILD FAILED!
            echo ========================================
            echo.
            echo Please check the error messages above
        )
    ) else (
        echo ERROR: Java installation test failed
    )
) else (
    echo.
    echo ========================================
    echo JAVA NOT FOUND!
    echo ========================================
    echo.
    echo Please install Java JDK 11+ from one of these sources:
    echo.
    echo 1. Eclipse Adoptium (Recommended):
    echo    https://adoptium.net/
    echo.
    echo 2. Oracle JDK:
    echo    https://www.oracle.com/java/technologies/downloads/
    echo.
    echo After installation, run this script again.
    echo.
    echo Manual setup:
    echo 1. Install Java JDK 11+
    echo 2. Set JAVA_HOME environment variable
    echo 3. Add %%JAVA_HOME%%\bin to PATH
    echo 4. Run: gradlew.bat assembleDebug
)

echo.
pause
