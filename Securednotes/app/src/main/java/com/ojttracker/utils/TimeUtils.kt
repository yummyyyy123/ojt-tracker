package com.ojttracker.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {
    
    fun formatMinutes(minutes: Int): String {
        if (minutes == 0) return "0h 0m"
        
        val hours = minutes / 60
        val mins = minutes % 60
        
        return when {
            hours > 0 && mins > 0 -> "${hours}h ${mins}m"
            hours > 0 -> "${hours}h 0m"
            else -> "0h ${mins}m"
        }
    }
    
    fun formatDuration(startTime: LocalDateTime, endTime: LocalDateTime): String {
        val duration = Duration.between(startTime, endTime)
        val minutes = duration.toMinutes().toInt()
        return formatMinutes(minutes)
    }
    
    fun getCurrentDateTimeString(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    
    fun getCurrentDateString(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    
    fun formatDateTimeForDisplay(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a"))
    }
    
    fun formatDateForDisplay(date: String): String {
        val localDate = java.time.LocalDate.parse(date)
        return localDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
    }
    
    fun getDayOfWeek(date: String): String {
        val localDate = java.time.LocalDate.parse(date)
        return localDate.dayOfWeek.toString().lowercase().replaceFirstChar { it.uppercase() }
    }
    
    fun getMonthYear(date: String): String {
        val localDate = java.time.LocalDate.parse(date)
        return localDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }
    
    fun getYearMonth(date: String): String {
        val localDate = java.time.LocalDate.parse(date)
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    }
    
    fun addMonthsToYearMonth(yearMonth: String, monthsToAdd: Int): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val date = java.time.YearMonth.parse(yearMonth, formatter)
        val newDate = date.plusMonths(monthsToAdd.toLong())
        return newDate.format(formatter)
    }
    
    fun isToday(date: String): Boolean {
        return date == getCurrentDateString()
    }
    
    fun calculateOvertime(totalMinutes: Int, regularHours: Int = 8): Int {
        val regularMinutes = regularHours * 60
        return maxOf(0, totalMinutes - regularMinutes)
    }
}
