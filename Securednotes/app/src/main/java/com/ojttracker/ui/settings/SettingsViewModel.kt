package com.ojttracker.ui.settings

import androidx.lifecycle.*
import com.ojttracker.data.repository.TimeRecordRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: TimeRecordRepository
) : ViewModel() {
    
    private val _settingsSaved = MutableLiveData<Boolean>()
    val settingsSaved: LiveData<Boolean> = _settingsSaved
    
    private val _dataCleared = MutableLiveData<Boolean>()
    val dataCleared: LiveData<Boolean> = _dataCleared
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun saveUserProfile(
        employeeName: String,
        employeeId: String,
        requiredHoursPerDay: Int,
        notificationsEnabled: Boolean,
        clockOutReminderEnabled: Boolean
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                // Save to SharedPreferences (implementation would go here)
                // For now, we'll just set the success flag
                _settingsSaved.value = true
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to save settings: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun exportAllData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                // Export all data (implementation would go here)
                // For now, we'll show a success message
                _errorMessage.value = "Export functionality coming soon"
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to export data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearAllData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                // Clear all time records
                repository.deleteAllTimeRecords()
                _dataCleared.value = true
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to clear data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun getEmployeeName(): String {
        // Get from SharedPreferences (implementation would go here)
        return ""
    }
    
    fun getEmployeeId(): String {
        // Get from SharedPreferences (implementation would go here)
        return ""
    }
    
    fun getRequiredHoursPerDay(): Int {
        // Get from SharedPreferences (implementation would go here)
        return 8
    }
    
    fun isNotificationsEnabled(): Boolean {
        // Get from SharedPreferences (implementation would go here)
        return true
    }
    
    fun isClockOutReminderEnabled(): Boolean {
        // Get from SharedPreferences (implementation would go here)
        return true
    }
    
    fun clearSettingsSavedFlag() {
        _settingsSaved.value = null
    }
    
    fun clearDataClearedFlag() {
        _dataCleared.value = null
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}

class SettingsViewModelFactory(
    private val repository: TimeRecordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
