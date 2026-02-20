@echo off
echo ========================================
echo Java Location Finder
echo ========================================
echo.

echo Searching for Java in all possible locations...
echo.

REM Check Program Files
echo Checking C:\Program Files\Java\
if exist "C:\Program Files\Java\jdk-17" (
    echo Found: C:\Program Files\Java\jdk-17
    set JAVA_HOME=C:\Program Files\Java\jdk-17
    goto configure
)

if exist "C:\Program Files\Java\jdk-17.0.*" (
    for /d %%i in ("C:\Program Files\Java\jdk-17.0.*") do (
        echo Found: %%i
        set JAVA_HOME=%%i
        goto configure
    )
)

REM Check Program Files (x86)
echo Checking C:\Program Files (x86)\Java\
if exist "C:\Program Files (x86)\Java\jdk-17" (
    echo Found: C:\Program Files (x86)\Java\jdk-17
    set JAVA_HOME=C:\Program Files (x86)\Java\jdk-17
    goto configure
)

REM Check Eclipse Adoptium
echo Checking C:\Program Files\Eclipse Adoptium\
for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do (
    echo Found: %%i
    set JAVA_HOME=%%i
    goto configure
)

REM Check user AppData
echo Checking %LOCALAPPDATA%\Programs\
for /d %%i in ("%LOCALAPPDATA%\Programs\Eclipse Adoptium\jdk-17*") do (
    echo Found: %%i
    set JAVA_HOME=%%i
    goto configure
)

REM Check user directory
echo Checking C:\Users\%USERNAME%\
for /d %%i in ("C:\Users\%USERNAME%\jdk-17*") do (
    echo Found: %%i
    set JAVA_HOME=%%i
    goto configure
)

REM Search entire C: drive (slower but thorough)
echo Searching C:\ drive for java.exe...
for /f "delims=" %%i in ('dir C:\java.exe /s /b 2^>nul') do (
    for %%j in ("%%i\..") do (
        echo Found Java at: %%j
        set JAVA_HOME=%%j
        goto configure
    )
)

echo Java not found automatically.
echo.
echo Please tell me where you installed Java,
echo or check these common locations manually:
echo.
echo - C:\Program Files\Java\
echo - C:\Program Files (x86)\Java\
echo - C:\Program Files\Eclipse Adoptium\
echo - C:\Users\%USERNAME%\AppData\Local\Programs\
echo.
goto manual

:configure
echo.
echo ========================================
echo CONFIGURING JAVA
echo ========================================
echo.
echo JAVA_HOME will be set to: %JAVA_HOME%
echo.

REM Test if java.exe exists
if exist "%JAVA_HOME%\bin\java.exe" (
    echo Found java.exe at: %JAVA_HOME%\bin\java.exe
    
    echo Setting environment variables...
    setx JAVA_HOME "%JAVA_HOME%" /M
    setx PATH "%JAVA_HOME%\bin;%PATH%" /M
    
    echo.
    echo SUCCESS! Environment variables set.
    echo.
    echo Please restart Command Prompt and run:
    echo gradlew.bat assembleDebug
    
    echo.
    echo Testing current installation...
    "%JAVA_HOME%\bin\java.exe" -version
) else (
    echo ERROR: java.exe not found in %JAVA_HOME%\bin\
    echo Please check installation.
)

:manual
echo.
echo Manual Setup:
echo 1. Find your Java installation folder
echo 2. Run these commands (replace with actual path):
echo    setx JAVA_HOME "C:\Your\Java\Path\jdk-17" /M
echo    setx PATH "%%JAVA_HOME%%\bin;%%PATH%%" /M
echo 3. Restart Command Prompt
echo 4. Run: gradlew.bat assembleDebug

echo.
pause
