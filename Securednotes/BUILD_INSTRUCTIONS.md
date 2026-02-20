# Build Instructions for OJT Tracker APK

## Prerequisites

Before building the APK, you need to install the following:

### 1. Java Development Kit (JDK)
- **Required Version**: JDK 11 or higher
- **Recommended**: JDK 17

**Installation Options:**
- Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
- Or use [OpenJDK](https://adoptium.net/)
- Or install via package manager (Chocolatey, Scoop, etc.)

**After Installation:**
1. Set `JAVA_HOME` environment variable
2. Add Java bin directory to PATH

**Example Environment Variables:**
```
JAVA_HOME=C:\Program Files\Java\jdk-17
PATH=%PATH%;%JAVA_HOME%\bin
```

### 2. Android SDK (Optional)
- Required for advanced builds and signing
- Install via [Android Studio](https://developer.android.com/studio)
- Set `ANDROID_HOME` environment variable

## Build Methods

### Method 1: Using Gradle Wrapper (Recommended)

1. **Open Command Prompt** or **PowerShell** as Administrator
2. **Navigate to project directory:**
   ```cmd
   cd C:\Users\msiqw\Desktop\Securednotes
   ```

3. **Build Debug APK:**
   ```cmd
   gradlew.bat assembleDebug
   ```

4. **Build Release APK (requires signing):**
   ```cmd
   gradlew.bat assembleRelease
   ```

### Method 2: Using Android Studio

1. **Open Android Studio**
2. **Open Project:** `C:\Users\msiqw\Desktop\Securednotes`
3. **Build → Build Bundle(s) / APK(s) → Build APK(s)**
4. **Select Debug or Release variant**

### Method 3: Manual Gradle (if gradle is installed)

```cmd
gradle assembleDebug
```

## Build Output Locations

### Debug APK
- **Location:** `app\build\outputs\apk\debug\app-debug.apk`
- **Size:** ~5-10 MB
- **Usage:** Development and testing

### Release APK
- **Location:** `app\build\outputs\apk\release\app-release.apk`
- **Size:** ~3-8 MB (smaller due to ProGuard)
- **Usage:** Production distribution

## Build Commands Reference

```cmd
# Clean build
gradlew.bat clean

# Build debug APK
gradlew.bat assembleDebug

# Build release APK
gradlew.bat assembleRelease

# Build both debug and release
gradlew.bat assemble

# Install debug APK to connected device
gradlew.bat installDebug

# Generate signed APK (requires keystore configuration)
gradlew.bat assembleRelease
```

## Troubleshooting

### Common Issues

1. **"JAVA_HOME is not set"**
   - Install JDK and set JAVA_HOME environment variable
   - Verify with: `echo %JAVA_HOME%`

2. **"java command not found"**
   - Add Java bin directory to PATH
   - Verify with: `java -version`

3. **"Gradle connection timeout"**
   - Check internet connection
   - Run: `gradlew.bat --refresh-dependencies`

4. **"License acceptance required"**
   - Run: `gradlew.bat --accept-licenses`

5. **"Build failed with errors"**
   - Check build logs for specific errors
   - Run: `gradlew.bat assembleDebug --stacktrace`

### Build Variants

| Variant | Description | Use Case |
|---------|-------------|----------|
| debug | Debuggable, not optimized | Development |
| release | Optimized, minified | Production |
| releaseUnsigned | Release without signing | Testing |

## APK Signing (Release Builds)

For production release APKs, you need to sign the APK:

1. **Generate Keystore:**
   ```cmd
   keytool -genkey -v -keystore ojt-tracker.keystore -alias ojt-tracker -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Add signing config to `app/build.gradle`:**
   ```gradle
   android {
       ...
       signingConfigs {
           release {
               storeFile file('ojt-tracker.keystore')
               storePassword 'your-store-password'
               keyAlias 'ojt-tracker'
               keyPassword 'your-key-password'
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
           }
       }
   }
   ```

## Quick Start Script

Create a batch file `build-apk.bat`:

```batch
@echo off
echo Building OJT Tracker APK...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install JDK 11+ and set JAVA_HOME
    pause
    exit /b 1
)

REM Build the APK
call gradlew.bat assembleDebug

if %errorlevel% equ 0 (
    echo.
    echo SUCCESS: APK built successfully!
    echo Location: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo ERROR: Build failed!
)

pause
```

## Installation

### Installing Debug APK
1. Enable "Unknown Sources" in Android settings
2. Transfer APK to device
3. Tap APK file to install

### Installing via ADB
```cmd
adb install app\build\outputs\apk\debug\app-debug.apk
```

## Project Information

- **Application ID:** com.ojttracker
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34

## Support

For build issues:
1. Check Java installation
2. Verify environment variables
3. Review build logs
4. Ensure sufficient disk space
5. Check internet connection for dependencies

---

**Note:** This project requires Java JDK 11+ to build. The gradle wrapper is included, so you don't need to install Gradle separately.
