@echo off
echo ========================================
echo OJT Tracker APK Builder
echo ========================================
echo.

REM Try to find Java automatically
echo Searching for Java installation...

REM Check common Java paths
if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-17
    goto java_found
)

if exist "C:\Program Files\Java\jdk-11\bin\java.exe" (
    set JAVA_HOME=C:\Program Files\Java\jdk-11
    goto java_found
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-17" (
    for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do (
        set JAVA_HOME=%%i
        goto java_found
    )
)

echo Java not found in standard locations.
echo.
echo Please set JAVA_HOME manually and run:
echo gradlew.bat assembleDebug
echo.
goto manual_setup

:java_found
echo Found Java at: %JAVA_HOME%
echo.
echo Setting environment variables...
set PATH=%JAVA_HOME%\bin;%PATH%

echo Testing Java...
"%JAVA_HOME%\bin\java.exe" -version
if errorlevel 1 goto java_error

echo.
echo Building APK...
call gradlew.bat assembleDebug

if errorlevel 0 (
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo APK Location: app\build\outputs\apk\debug\app-debug.apk
    goto end
) else (
    echo Build failed. Check error messages above.
    goto end
)

:java_error
echo Java test failed. Please check installation.
goto end

:manual_setup
echo Manual setup required:
echo 1. Install Java JDK 11+
echo 2. Set JAVA_HOME environment variable
echo 3. Add %%JAVA_HOME%%\bin to PATH
echo 4. Run: gradlew.bat assembleDebug

:end
echo.
pause
