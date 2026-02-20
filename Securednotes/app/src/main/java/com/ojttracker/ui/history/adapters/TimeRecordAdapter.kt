package com.ojttracker.ui.history.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ojttracker.databinding.ItemDtrRecordBinding
import com.ojttracker.data.database.entities.TimeRecord

class TimeRecordAdapter(
    private val onItemClick: (TimeRecord) -> Unit
) : ListAdapter<TimeRecord, TimeRecordAdapter.TimeRecordViewHolder>(TimeRecordDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeRecordViewHolder {
        val binding = ItemDtrRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimeRecordViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: TimeRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class TimeRecordViewHolder(
        private val binding: ItemDtrRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(timeRecord: TimeRecord) {
            binding.apply {
                tvDate.text = timeRecord.date
                tvDay.text = timeRecord.dayOfWeek
                tvTimeIn.text = timeRecord.formattedTimeIn
                tvTimeOut.text = timeRecord.formattedTimeOut
                tvDuration.text = timeRecord.formattedDuration
                
                // Show overtime badge if applicable
                if (timeRecord.overtimeMinutes > 0) {
                    tvOvertimeBadge.text = "Overtime: ${formatMinutes(timeRecord.overtimeMinutes)}"
                    tvOvertimeBadge.visibility = View.VISIBLE
                } else {
                    tvOvertimeBadge.visibility = View.GONE
                }
                
                // Set click listener
                root.setOnClickListener {
                    onItemClick(timeRecord)
                }
            }
        }
        
        private fun formatMinutes(minutes: Int): String {
            val hours = minutes / 60
            val mins = minutes % 60
            return when {
                hours > 0 && mins > 0 -> "${hours}h ${mins}m"
                hours > 0 -> "${hours}h 0m"
                else -> "0h ${mins}m"
            }
        }
    }
}

class TimeRecordDiffCallback : DiffUtil.ItemCallback<TimeRecord>() {
    override fun areItemsTheSame(oldItem: TimeRecord, newItem: TimeRecord): Boolean {
        return oldItem.id == newItem.id
    }
    
    override fun areContentsTheSame(oldItem: TimeRecord, newItem: TimeRecord): Boolean {
        return oldItem == newItem
    }
}
