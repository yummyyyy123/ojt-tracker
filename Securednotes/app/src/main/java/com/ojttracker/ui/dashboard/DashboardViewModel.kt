package com.ojttracker.ui.dashboard

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.ojttracker.data.repository.TimeRecordRepository
import com.ojttracker.data.repository.DailyStatistics
import com.ojttracker.data.repository.WeeklyStatistics
import com.ojttracker.data.repository.MonthlyStatistics
import com.ojttracker.data.database.entities.TimeRecord

class DashboardViewModel(
    private val repository: TimeRecordRepository
) : ViewModel() {
    
    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String> = _currentTime
    
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate
    
    private val _activeTimeRecord = MutableLiveData<TimeRecord?>()
    val activeTimeRecord: LiveData<TimeRecord?> = _activeTimeRecord
    
    private val _sessionStatus = MutableLiveData<String>()
    val sessionStatus: LiveData<String> = _sessionStatus
    
    private val _clockButtonState = MutableLiveData<ClockButtonState>()
    val clockButtonState: LiveData<ClockButtonState> = _clockButtonState
    
    private val _todayStatistics = MutableLiveData<DailyStatistics>()
    val todayStatistics: LiveData<DailyStatistics> = _todayStatistics
    
    private val _weeklyStatistics = MutableLiveData<WeeklyStatistics>()
    val weeklyStatistics: LiveData<WeeklyStatistics> = _weeklyStatistics
    
    private val _monthlyStatistics = MutableLiveData<MonthlyStatistics>()
    val monthlyStatistics: LiveData<MonthlyStatistics> = _monthlyStatistics
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    init {
        updateCurrentTime()
        checkActiveSession()
        loadStatistics()
    }
    
    private fun updateCurrentTime() {
        val now = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        
        _currentTime.value = now.format(timeFormatter)
        _currentDate.value = now.format(dateFormatter)
    }
    
    fun refreshTime() {
        updateCurrentTime()
    }
    
    private fun checkActiveSession() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val activeRecord = repository.getActiveTimeRecord()
                _activeTimeRecord.value = activeRecord
                
                if (activeRecord != null) {
                    _sessionStatus.value = "Active session started at ${activeRecord.formattedTimeIn}"
                    _clockButtonState.value = ClockButtonState.CLOCK_OUT
                } else {
                    _sessionStatus.value = "No active time session"
                    _clockButtonState.value = ClockButtonState.CLOCK_IN
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to check active session: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun loadStatistics() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                val today = LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                val todayStats = repository.getTodayStatistics(today)
                _todayStatistics.value = todayStats
                
                val weeklyStats = repository.getWeeklyStatistics()
                _weeklyStatistics.value = weeklyStats
                
                val currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                val monthlyStats = repository.getMonthlyStatistics(currentMonth)
                _monthlyStatistics.value = monthlyStats
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load statistics: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun onClockAction() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                when (_clockButtonState.value) {
                    ClockButtonState.CLOCK_IN -> {
                        val record = repository.clockIn()
                        _activeTimeRecord.value = record
                        _sessionStatus.value = "Active session started at ${record.formattedTimeIn}"
                        _clockButtonState.value = ClockButtonState.CLOCK_OUT
                    }
                    ClockButtonState.CLOCK_OUT -> {
                        val record = repository.clockOut()
                        if (record != null) {
                            _activeTimeRecord.value = null
                            _sessionStatus.value = "Session completed. Duration: ${record.formattedDuration}"
                            _clockButtonState.value = ClockButtonState.CLOCK_IN
                            // Refresh statistics after clock out
                            loadStatistics()
                        }
                    }
                    null -> {
                        // Handle null case
                        checkActiveSession()
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Clock action failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    fun refreshData() {
        updateCurrentTime()
        checkActiveSession()
        loadStatistics()
    }
}

enum class ClockButtonState {
    CLOCK_IN,
    CLOCK_OUT
}

class DashboardViewModelFactory(
    private val repository: TimeRecordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
