# OJT Tracker - Professional Time Tracking App

A complete Android application for tracking On-the-Job Training (OJT) time with comprehensive Daily Time Record (DTR) functionality.

## Features

### 🕐 Time Tracking
- **Clock In/Clock Out** functionality with real-time display
- **Active Session Tracking** - Shows current session duration
- **Automatic Duration Calculation** - Calculates work hours and overtime
- **Session Status Display** - Clear indication of active/inactive sessions

### 📊 Dashboard
- **Real-time Clock** - Live time display with seconds
- **Daily Statistics** - Total hours worked today
- **Weekly Overview** - Total hours for current week
- **Monthly Summary** - Monthly hours, days worked, and average
- **Quick Actions** - Easy access to history and export

### 📋 DTR History
- **Monthly View** - Navigate through different months
- **Detailed Records** - Time in, time out, duration for each day
- **Overtime Tracking** - Automatic overtime calculation
- **Monthly Statistics** - Summary cards with totals
- **Export Functionality** - CSV export for reporting

### ⚙️ Settings
- **User Profile** - Employee name and ID
- **Work Settings** - Required hours per day
- **Notifications** - Enable/disable reminders
- **Data Management** - Export and clear data options

## Technical Architecture

### 🏗️ Architecture Pattern
- **MVVM (Model-View-ViewModel)** with Android Architecture Components
- **Repository Pattern** for data access
- **Dependency Injection** with Hilt
- **Room Database** for local storage
- **Navigation Component** for app navigation

### 🛠️ Technology Stack
- **Kotlin** - Primary programming language
- **Android Jetpack** - Modern Android development
- **Material Design 3** - Professional UI/UX
- **Coroutines** - Asynchronous programming
- **LiveData & Flow** - Reactive programming
- **Room Database** - Local data persistence
- **Dagger Hilt** - Dependency injection
- **Navigation Component** - Fragment navigation

### 📱 UI Components
- **Material Cards** - Modern card-based design
- **Bottom Navigation** - Easy navigation between sections
- **Navigation Drawer** - Additional menu options
- **Floating Action Buttons** - Quick access to key features
- **RecyclerView** - Efficient list display
- **Material Buttons & Text Fields** - Consistent design language

## Project Structure

```
app/
├── src/main/
│   ├── java/com/ojttracker/
│   │   ├── data/
│   │   │   ├── database/
│   │   │   │   ├── entities/          # Database entities
│   │   │   │   ├── dao/              # Data Access Objects
│   │   │   │   └── AppDatabase.kt    # Room database
│   │   │   └── repository/           # Repository layer
│   │   ├── ui/
│   │   │   ├── dashboard/            # Main dashboard
│   │   │   ├── history/              # DTR history
│   │   │   ├── settings/             # App settings
│   │   │   └── export/               # Export functionality
│   │   ├── di/                       # Dependency injection modules
│   │   ├── utils/                    # Utility classes
│   │   └── MainActivity.kt           # Main activity
│   ├── res/
│   │   ├── layout/                   # XML layouts
│   │   ├── values/                   # Strings, colors, themes
│   │   ├── drawable/                 # Icons and drawables
│   │   ├── menu/                     # Navigation menus
│   │   └── navigation/               # Navigation graph
│   └── AndroidManifest.xml
└── build.gradle                      # App-level build configuration
```

## Key Features Implementation

### 🕐 Time Tracking System
- **Clock In**: Records current timestamp as start time
- **Clock Out**: Calculates duration and saves completed record
- **Active Session**: Tracks ongoing work session
- **Overtime Calculation**: Automatically calculates overtime beyond 8 hours

### 📊 Statistics Engine
- **Real-time Updates**: Live statistics refresh
- **Multiple Views**: Daily, weekly, monthly statistics
- **Duration Formatting**: Human-readable time display
- **Data Aggregation**: Efficient database queries

### 🗄️ Database Design
- **TimeRecord Entity**: Complete time tracking data
- **Indexed Queries**: Optimized for performance
- **Data Validation**: Ensures data integrity
- **Migration Support**: Future database updates

## Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 24+ (Android 7.0)
- Kotlin support enabled

### Build Instructions
1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Build and run on device/emulator

### Dependencies
- Android Jetpack components
- Material Design components
- Room database
- Dagger Hilt
- Kotlin coroutines
- Navigation component

## Usage Guide

### First Time Setup
1. Open the app
2. Navigate to Settings
3. Enter your employee details
4. Configure work preferences

### Daily Usage
1. **Clock In**: Tap the green "Clock In" button when starting work
2. **Track Time**: Monitor your session duration on dashboard
3. **Clock Out**: Tap the red "Clock Out" button when finished
4. **Review**: Check history for detailed records

### Exporting Data
1. Navigate to History tab
2. Select desired month
3. Tap export button (FAB)
4. Choose export location
5. Share CSV file as needed

## Future Enhancements

### 🚀 Planned Features
- **Location Tracking** - GPS-based clock in/out
- **Biometric Authentication** - Secure clock verification
- **Cloud Sync** - Multi-device synchronization
- **Advanced Analytics** - Detailed time analysis
- **Notifications** - Smart reminders and alerts
- **Themes** - Dark/light mode support
- **Multi-language** - Internationalization support

### 🔧 Technical Improvements
- **Unit Tests** - Comprehensive test coverage
- **CI/CD Pipeline** - Automated build and deployment
- **Performance Optimization** - Enhanced app performance
- **Security Enhancements** - Data encryption and protection

## Contributing

This project is designed as a complete, production-ready OJT tracking solution. The codebase follows Android development best practices and modern architecture patterns.

## License

This project is provided as a complete solution for OJT time tracking needs.

---

**OJT Tracker** - Professional time tracking made simple and efficient.
