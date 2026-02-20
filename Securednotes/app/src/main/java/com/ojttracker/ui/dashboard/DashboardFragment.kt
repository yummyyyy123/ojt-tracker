package com.ojttracker.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ojttracker.R
import com.ojttracker.databinding.FragmentDashboardBinding
import com.ojttracker.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var viewModelFactory: DashboardViewModelFactory
    
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }
    
    private val timeUpdateHandler = Handler(Looper.getMainLooper())
    private val timeUpdateRunnable = object : Runnable {
        override fun run() {
            viewModel.refreshTime()
            timeUpdateHandler.postDelayed(this, 1000) // Update every second
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupClickListeners()
        
        // Start time updates
        timeUpdateHandler.post(timeUpdateRunnable)
    }
    
    private fun setupObservers() {
        // Current time
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { time ->
            binding.tvCurrentTime.text = time
        })
        
        // Current date
        viewModel.currentDate.observe(viewLifecycleOwner, Observer { date ->
            binding.tvCurrentDate.text = date
        })
        
        // Session status
        viewModel.sessionStatus.observe(viewLifecycleOwner, Observer { status ->
            binding.tvSessionStatus.text = status
        })
        
        // Clock button state
        viewModel.clockButtonState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ClockButtonState.CLOCK_IN -> {
                    binding.btnClockAction.text = getString(R.string.clock_in)
                    binding.btnClockAction.setBackgroundColor(resources.getColor(R.color.clock_in_bg, null))
                }
                ClockButtonState.CLOCK_OUT -> {
                    binding.btnClockAction.text = getString(R.string.clock_out)
                    binding.btnClockAction.setBackgroundColor(resources.getColor(R.color.clock_out_bg, null))
                }
            }
        })
        
        // Today's statistics
        viewModel.todayStatistics.observe(viewLifecycleOwner, Observer { stats ->
            binding.tvHoursToday.text = TimeUtils.formatMinutes(stats.totalMinutes)
        })
        
        // Weekly statistics
        viewModel.weeklyStatistics.observe(viewLifecycleOwner, Observer { stats ->
            binding.tvHoursWeek.text = TimeUtils.formatMinutes(stats.totalMinutes)
        })
        
        // Monthly statistics
        viewModel.monthlyStatistics.observe(viewLifecycleOwner, Observer { stats ->
            binding.tvHoursMonth.text = TimeUtils.formatMinutes(stats.totalMinutes)
            binding.tvDaysWorked.text = stats.daysWorked.toString()
            binding.tvAvgHours.text = TimeUtils.formatMinutes(stats.averageMinutesPerDay)
        })
        
        // Loading state
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.btnClockAction.isEnabled = !isLoading
        })
        
        // Error messages
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        })
    }
    
    private fun setupClickListeners() {
        binding.btnClockAction.setOnClickListener {
            viewModel.onClockAction()
        }
        
        binding.btnViewHistory.setOnClickListener {
            findNavController().navigate(R.id.nav_history)
        }
        
        binding.btnExport.setOnClickListener {
            // Navigate to export functionality
            findNavController().navigate(R.id.nav_export)
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh data when fragment becomes visible
        viewModel.refreshData()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        // Stop time updates
        timeUpdateHandler.removeCallbacks(timeUpdateRunnable)
        _binding = null
    }
}
