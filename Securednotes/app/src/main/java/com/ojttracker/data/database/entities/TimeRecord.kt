package com.ojttracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity(tableName = "time_records")
@Parcelize
data class TimeRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: yyyy-MM-dd
    val timeIn: LocalDateTime,
    val timeOut: LocalDateTime? = null,
    val durationMinutes: Int? = null, // Calculated duration in minutes
    val overtimeMinutes: Int = 0, // Overtime in minutes
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val formattedDuration: String
        get() = durationMinutes?.let { minutes ->
            val hours = minutes / 60
            val mins = minutes % 60
            "${hours}h ${mins}m"
        } ?: "In Progress"

    val formattedTimeIn: String
        get() = String.format("%02d:%02d %s", 
            timeIn.hour % 12, timeIn.minute, 
            if (timeIn.hour >= 12) "PM" else "AM")

    val formattedTimeOut: String
        get() = timeOut?.let { timeOut ->
            String.format("%02d:%02d %s", 
                timeOut.hour % 12, timeOut.minute, 
                if (timeOut.hour >= 12) "PM" else "AM")
        } ?: "In Progress"

    val dayOfWeek: String
        get() {
            val day = timeIn.dayOfWeek.value
            return when (day) {
                1 -> "Monday"
                2 -> "Tuesday"
                3 -> "Wednesday"
                4 -> "Thursday"
                5 -> "Friday"
                6 -> "Saturday"
                7 -> "Sunday"
                else -> "Unknown"
            }
        }
}
