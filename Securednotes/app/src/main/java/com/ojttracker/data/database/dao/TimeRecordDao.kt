package com.ojttracker.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.ojttracker.data.database.entities.TimeRecord

@Dao
interface TimeRecordDao {
    
    @Query("SELECT * FROM time_records ORDER BY date DESC, timeIn DESC")
    fun getAllTimeRecords(): Flow<List<TimeRecord>>
    
    @Query("SELECT * FROM time_records WHERE date = :date LIMIT 1")
    suspend fun getTimeRecordByDate(date: String): TimeRecord?
    
    @Query("SELECT * FROM time_records WHERE isCompleted = 0 LIMIT 1")
    suspend fun getActiveTimeRecord(): TimeRecord?
    
    @Query("SELECT * FROM time_records WHERE date LIKE :yearMonth ORDER BY date DESC")
    suspend fun getTimeRecordsByMonth(yearMonth: String): List<TimeRecord>
    
    @Query("SELECT * FROM time_records WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    suspend fun getTimeRecordsByDateRange(startDate: String, endDate: String): List<TimeRecord>
    
    @Query("SELECT SUM(durationMinutes) FROM time_records WHERE date LIKE :yearMonth AND isCompleted = 1")
    suspend fun getTotalMinutesByMonth(yearMonth: String): Int?
    
    @Query("SELECT SUM(overtimeMinutes) FROM time_records WHERE date LIKE :yearMonth AND isCompleted = 1")
    suspend fun getTotalOvertimeMinutesByMonth(yearMonth: String): Int?
    
    @Query("SELECT COUNT(*) FROM time_records WHERE date LIKE :yearMonth AND isCompleted = 1")
    suspend fun getCompletedDaysCountByMonth(yearMonth: String): Int?
    
    @Query("SELECT SUM(durationMinutes) FROM time_records WHERE date = :date AND isCompleted = 1")
    suspend fun getTodayMinutes(date: String): Int?
    
    @Query("SELECT SUM(durationMinutes) FROM time_records WHERE date >= :startDate AND date <= :endDate AND isCompleted = 1")
    suspend fun getTotalMinutesByDateRange(startDate: String, endDate: String): Int?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeRecord(timeRecord: TimeRecord): Long
    
    @Update
    suspend fun updateTimeRecord(timeRecord: TimeRecord)
    
    @Delete
    suspend fun deleteTimeRecord(timeRecord: TimeRecord)
    
    @Query("DELETE FROM time_records WHERE id = :id")
    suspend fun deleteTimeRecordById(id: Long)
    
    @Query("DELETE FROM time_records")
    suspend fun deleteAllTimeRecords()
}
