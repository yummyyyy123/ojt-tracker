@echo off
echo ========================================
echo Finding Java JDK 17 Installation
echo ========================================
echo.

echo Checking common installation paths...

REM Check Program Files
if exist "C:\Program Files\Java\jdk-17" (
    echo Found JDK 17 at: C:\Program Files\Java\jdk-17
    echo.
    echo Setting JAVA_HOME...
    setx JAVA_HOME "C:\Program Files\Java\jdk-17" /M
    echo Adding to PATH...
    setx PATH "%JAVA_HOME%\bin;%PATH%" /M
    echo.
    echo Environment variables set! Please restart Command Prompt.
    goto test_java
)

REM Check Program Files (x86)
if exist "C:\Program Files (x86)\Java\jdk-17" (
    echo Found JDK 17 at: C:\Program Files (x86)\Java\jdk-17
    echo.
    echo Setting JAVA_HOME...
    setx JAVA_HOME "C:\Program Files (x86)\Java\jdk-17" /M
    echo Adding to PATH...
    setx PATH "%JAVA_HOME%\bin;%PATH%" /M
    echo.
    echo Environment variables set! Please restart Command Prompt.
    goto test_java
)

REM Check Eclipse Adoptium
for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do (
    echo Found Adoptium JDK 17 at: %%i
    echo.
    echo Setting JAVA_HOME...
    setx JAVA_HOME "%%i" /M
    echo Adding to PATH...
    setx PATH "%%i\bin;%PATH%" /M
    echo.
    echo Environment variables set! Please restart Command Prompt.
    goto test_java
)

REM Check user AppData
for /d %%i in ("%LOCALAPPDATA%\Programs\Eclipse Adoptium\jdk-17*") do (
    echo Found Adoptium JDK 17 at: %%i
    echo.
    echo Setting JAVA_HOME...
    setx JAVA_HOME "%%i" /M
    echo Adding to PATH...
    setx PATH "%%i\bin;%PATH%" /M
    echo.
    echo Environment variables set! Please restart Command Prompt.
    goto test_java
)

echo JDK 17 not found in standard locations.
echo.
echo Please check if Java was installed in a custom location.
echo Common locations to check:
echo - C:\Program Files\Java\
echo - C:\Program Files\Eclipse Adoptium\
echo - C:\Users\%USERNAME%\AppData\Local\Programs\Eclipse Adoptium\
echo.
goto manual_setup

:test_java
echo.
echo Testing Java installation...
"C:\Program Files\Java\jdk-17\bin\java.exe" -version 2>nul
if errorlevel 1 (
    echo Test failed. You may need to restart Command Prompt.
) else (
    echo SUCCESS! Java is working.
    echo.
    echo Now you can build the APK with:
    echo gradlew.bat assembleDebug
)
goto end

:manual_setup
echo Manual Setup Required:
echo 1. Find your Java installation directory
echo 2. Run these commands (replace with your actual path):
echo    setx JAVA_HOME "C:\Path\To\Your\jdk-17" /M
echo    setx PATH "%%JAVA_HOME%%\bin;%%PATH%%" /M
echo 3. Restart Command Prompt
echo 4. Run: gradlew.bat assembleDebug

:end
echo.
pause
