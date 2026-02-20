# OJT Tracker APK Information

## Project Summary
- **App Name:** OJT Tracker
- **Package Name:** com.ojttracker
- **Version:** 1.0 (versionCode: 1)
- **Minimum Android Version:** 7.0 (API 24)
- **Target Android Version:** 14 (API 34)

## Expected APK Specifications

### Debug APK
- **Estimated Size:** 8-12 MB
- **Location:** `app/build/outputs/apk/debug/app-debug.apk`
- **Features:** Debuggable, includes logging, not optimized

### Release APK
- **Estimated Size:** 5-8 MB
- **Location:** `app/build/outputs/apk/release/app-release.apk`
- **Features:** Optimized, minified, requires signing

## App Features Included in APK

### Core Functionality
- ✅ Time tracking (Clock In/Out)
- ✅ Real-time dashboard
- ✅ DTR history with monthly navigation
- ✅ Statistics and summaries
- ✅ Settings and preferences
- ✅ Data export functionality
- ✅ Professional Material Design 3 UI

### Technical Features
- ✅ Room database for local storage
- ✅ MVVM architecture with LiveData
- ✅ Dependency injection with Hilt
- ✅ Material Design components
- ✅ Navigation component
- ✅ Coroutines for async operations

## Permissions Required
- `ACCESS_FINE_LOCATION` (optional, for future location tracking)
- `ACCESS_COARSE_LOCATION` (optional)
- `WRITE_EXTERNAL_STORAGE` (for CSV export)
- `READ_EXTERNAL_STORAGE` (for file access)

## Build Requirements

### System Requirements
- **Java:** JDK 11 or higher
- **Android SDK:** API 24-34
- **Gradle:** 8.4 (included via wrapper)
- **Memory:** 4GB+ RAM recommended
- **Storage:** 2GB+ free space

### Build Commands
```bash
# Debug APK
gradlew.bat assembleDebug

# Release APK
gradlew.bat assembleRelease

# Clean and build
gradlew.bat clean assembleDebug
```

## Installation Instructions

### For Testing (Debug APK)
1. Enable "Unknown Sources" in Android Settings
2. Transfer `app-debug.apk` to device
3. Tap to install

### For Production (Release APK)
1. Sign the APK with your keystore
2. Enable "Unknown Sources" or use Play Store
3. Install signed APK

## Testing Checklist

After building the APK, test these features:

### Basic Functionality
- [ ] App launches successfully
- [ ] Clock In button works
- [ ] Clock Out button works
- [ ] Time displays correctly
- [ ] Dashboard statistics update

### Navigation
- [ ] Bottom navigation works
- [ ] Drawer navigation works
- [ ] Settings screen accessible
- [ ] History screen accessible

### Data Persistence
- [ ] Time records save correctly
- [ ] Data survives app restart
- [ ] History shows correct data
- [ ] Statistics calculate correctly

### UI/UX
- [ ] Material Design 3 theme applied
- [ ] Responsive layout on different screens
- [ ] Smooth transitions
- [ ] Error handling works

## Troubleshooting

### Build Issues
- **Java not found:** Install JDK 11+ and set JAVA_HOME
- **Gradle fails:** Check internet connection for dependencies
- **Memory errors:** Increase heap size with `-Xmx4g`
- **Permission errors:** Run as Administrator

### Runtime Issues
- **App crashes:** Check logcat with `adb logcat`
- **Database errors:** Clear app data and restart
- **UI issues:** Test on different screen sizes

## Next Steps

1. **Install Java JDK 11+**
2. **Run `build-apk.bat`** or use `gradlew.bat assembleDebug`
3. **Test the APK** on an Android device or emulator
4. **Optional:** Create release keystore for production builds

## Support Files Created

- `build-apk.bat` - Automated build script
- `BUILD_INSTRUCTIONS.md` - Detailed build guide
- `gradlew.bat` - Gradle wrapper for Windows
- `gradle/wrapper/` - Gradle wrapper configuration

---

**Ready to build once Java is installed!**
