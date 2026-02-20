package com.ojttracker.data.repository

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.ojttracker.data.database.dao.TimeRecordDao
import com.ojttracker.data.database.entities.TimeRecord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeRecordRepository @Inject constructor(
    private val timeRecordDao: TimeRecordDao
) {
    
    fun getAllTimeRecords(): Flow<List<TimeRecord>> {
        return timeRecordDao.getAllTimeRecords()
    }
    
    suspend fun getTimeRecordByDate(date: String): TimeRecord? {
        return timeRecordDao.getTimeRecordByDate(date)
    }
    
    suspend fun getActiveTimeRecord(): TimeRecord? {
        return timeRecordDao.getActiveTimeRecord()
    }
    
    suspend fun getTimeRecordsByMonth(yearMonth: String): List<TimeRecord> {
        return timeRecordDao.getTimeRecordsByMonth(yearMonth)
    }
    
    suspend fun getTimeRecordsByDateRange(startDate: String, endDate: String): List<TimeRecord> {
        return timeRecordDao.getTimeRecordsByDateRange(startDate, endDate)
    }
    
    suspend fun getMonthlyStatistics(yearMonth: String): MonthlyStatistics {
        val totalMinutes = timeRecordDao.getTotalMinutesByMonth(yearMonth) ?: 0
        val overtimeMinutes = timeRecordDao.getTotalOvertimeMinutesByMonth(yearMonth) ?: 0
        val daysWorked = timeRecordDao.getCompletedDaysCountByMonth(yearMonth) ?: 0
        
        return MonthlyStatistics(
            totalMinutes = totalMinutes,
            overtimeMinutes = overtimeMinutes,
            daysWorked = daysWorked,
            averageMinutesPerDay = if (daysWorked > 0) totalMinutes / daysWorked else 0
        )
    }
    
    suspend fun getTodayStatistics(date: String): DailyStatistics {
        val totalMinutes = timeRecordDao.getTodayMinutes(date) ?: 0
        return DailyStatistics(totalMinutes = totalMinutes)
    }
    
    suspend fun getWeeklyStatistics(): WeeklyStatistics {
        val today = LocalDate.now()
        val startOfWeek = today.minusDays(today.dayOfWeek.value - 1L)
        val endOfWeek = startOfWeek.plusDays(6L)
        
        val totalMinutes = timeRecordDao.getTotalMinutesByDateRange(
            startOfWeek.toString(),
            endOfWeek.toString()
        ) ?: 0
        
        return WeeklyStatistics(totalMinutes = totalMinutes)
    }
    
    suspend fun clockIn(): TimeRecord {
        val now = LocalDateTime.now()
        val today = now.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
        
        // Check if there's already a record for today
        val existingRecord = getTimeRecordByDate(today)
        
        return if (existingRecord != null) {
            // Update existing record
            val updatedRecord = existingRecord.copy(
                timeIn = now,
                isCompleted = false,
                updatedAt = now
            )
            timeRecordDao.updateTimeRecord(updatedRecord)
            updatedRecord
        } else {
            // Create new record
            val newRecord = TimeRecord(
                date = today,
                timeIn = now
            )
            val id = timeRecordDao.insertTimeRecord(newRecord)
            newRecord.copy(id = id)
        }
    }
    
    suspend fun clockOut(): TimeRecord? {
        val activeRecord = getActiveTimeRecord() ?: return null
        
        val now = LocalDateTime.now()
        val durationMinutes = kotlin.math.abs(
            java.time.Duration.between(activeRecord.timeIn, now).toMinutes().toInt()
        )
        
        // Calculate overtime (assuming 8 hours = 480 minutes is regular time)
        val overtimeMinutes = maxOf(0, durationMinutes - 480)
        
        val updatedRecord = activeRecord.copy(
            timeOut = now,
            durationMinutes = durationMinutes,
            overtimeMinutes = overtimeMinutes,
            isCompleted = true,
            updatedAt = now
        )
        
        timeRecordDao.updateTimeRecord(updatedRecord)
        return updatedRecord
    }
    
    suspend fun deleteTimeRecord(timeRecord: TimeRecord) {
        timeRecordDao.deleteTimeRecord(timeRecord)
    }
    
    suspend fun deleteTimeRecordById(id: Long) {
        timeRecordDao.deleteTimeRecordById(id)
    }
}

data class MonthlyStatistics(
    val totalMinutes: Int,
    val overtimeMinutes: Int,
    val daysWorked: Int,
    val averageMinutesPerDay: Int
)

data class DailyStatistics(
    val totalMinutes: Int
)

data class WeeklyStatistics(
    val totalMinutes: Int
)
