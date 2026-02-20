package com.ojttracker.ui.history

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.ojttracker.data.repository.TimeRecordRepository
import com.ojttracker.data.repository.MonthlyStatistics
import com.ojttracker.data.database.entities.TimeRecord

class HistoryViewModel(
    private val repository: TimeRecordRepository
) : ViewModel() {
    
    private val _currentMonth = MutableLiveData<String>()
    val currentMonth: LiveData<String> = _currentMonth
    
    private val _timeRecords = MutableLiveData<List<TimeRecord>>()
    val timeRecords: LiveData<List<TimeRecord>> = _timeRecords
    
    private val _monthlyStatistics = MutableLiveData<MonthlyStatistics>()
    val monthlyStatistics: LiveData<MonthlyStatistics> = _monthlyStatistics
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    private val _exportSuccess = MutableLiveData<Boolean>()
    val exportSuccess: LiveData<Boolean> = _exportSuccess
    
    init {
        val now = LocalDate.now()
        _currentMonth.value = now.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
        loadTimeRecords()
    }
    
    fun loadTimeRecords() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val yearMonth = getCurrentYearMonth()
                val records = repository.getTimeRecordsByMonth(yearMonth)
                _timeRecords.value = records
                
                val stats = repository.getMonthlyStatistics(yearMonth)
                _monthlyStatistics.value = stats
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load time records: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun navigateToPreviousMonth() {
        val currentYearMonth = getCurrentYearMonth()
        val previousMonth = addMonthsToYearMonth(currentYearMonth, -1)
        updateCurrentMonth(previousMonth)
        loadTimeRecords()
    }
    
    fun navigateToNextMonth() {
        val currentYearMonth = getCurrentYearMonth()
        val nextMonth = addMonthsToYearMonth(currentYearMonth, 1)
        updateCurrentMonth(nextMonth)
        loadTimeRecords()
    }
    
    private fun getCurrentYearMonth(): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        val monthYear = _currentMonth.value ?: return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val date = java.time.YearMonth.parse(monthYear, formatter)
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    }
    
    private fun addMonthsToYearMonth(yearMonth: String, monthsToAdd: Int): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val date = java.time.YearMonth.parse(yearMonth, formatter)
        val newDate = date.plusMonths(monthsToAdd.toLong())
        return newDate.format(formatter)
    }
    
    private fun updateCurrentMonth(yearMonth: String) {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        val date = java.time.YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"))
        _currentMonth.value = date.format(formatter)
    }
    
    fun deleteTimeRecord(timeRecord: TimeRecord) {
        viewModelScope.launch {
            try {
                repository.deleteTimeRecord(timeRecord)
                loadTimeRecords() // Refresh the list
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete record: ${e.message}"
            }
        }
    }
    
    fun exportToCSV() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val yearMonth = getCurrentYearMonth()
                val records = repository.getTimeRecordsByMonth(yearMonth)
                
                // This would trigger the export functionality
                // For now, we'll just set success flag
                _exportSuccess.value = true
                
            } catch (e: Exception) {
                _errorMessage.value = "Failed to export data: ${e.message}"
                _exportSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    fun clearExportSuccess() {
        _exportSuccess.value = null
    }
}

class HistoryViewModelFactory(
    private val repository: TimeRecordRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
