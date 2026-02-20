package com.ojttracker.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ojttracker.R
import com.ojttracker.databinding.FragmentHistoryBinding
import com.ojttracker.ui.history.adapters.TimeRecordAdapter
import com.ojttracker.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory
    
    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private lateinit var timeRecordAdapter: TimeRecordAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        timeRecordAdapter = TimeRecordAdapter { timeRecord ->
            // Handle item click if needed
        }
        
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timeRecordAdapter
        }
    }
    
    private fun setupObservers() {
        // Current month
        viewModel.currentMonth.observe(viewLifecycleOwner, Observer { month ->
            binding.tvCurrentMonth.text = month
        })
        
        // Time records
        viewModel.timeRecords.observe(viewLifecycleOwner, Observer { records ->
            timeRecordAdapter.submitList(records)
            
            // Show/hide empty state
            if (records.isEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }
        })
        
        // Monthly statistics
        viewModel.monthlyStatistics.observe(viewLifecycleOwner, Observer { stats ->
            binding.tvMonthTotalHours.text = TimeUtils.formatMinutes(stats.totalMinutes)
            binding.tvMonthDaysWorked.text = stats.daysWorked.toString()
            binding.tvMonthOvertime.text = TimeUtils.formatMinutes(stats.overtimeMinutes)
        })
        
        // Loading state
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            // Update UI loading state if needed
        })
        
        // Error messages
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        })
        
        // Export success
        viewModel.exportSuccess.observe(viewLifecycleOwner, Observer { success ->
            success?.let {
                if (it) {
                    Snackbar.make(binding.root, "DTR exported successfully", Snackbar.LENGTH_SHORT).show()
                }
                viewModel.clearExportSuccess()
            }
        })
    }
    
    private fun setupClickListeners() {
        binding.btnPreviousMonth.setOnClickListener {
            viewModel.navigateToPreviousMonth()
        }
        
        binding.btnNextMonth.setOnClickListener {
            viewModel.navigateToNextMonth()
        }
        
        binding.fabExport.setOnClickListener {
            viewModel.exportToCSV()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
