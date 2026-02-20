package com.ojttracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ojttracker.R
import com.ojttracker.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var viewModelFactory: SettingsViewModelFactory
    
    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupClickListeners()
        loadSettings()
    }
    
    private fun setupObservers() {
        viewModel.settingsSaved.observe(viewLifecycleOwner, Observer { saved ->
            if (saved) {
                Snackbar.make(binding.root, "Settings saved successfully", Snackbar.LENGTH_SHORT).show()
                viewModel.clearSettingsSavedFlag()
            }
        })
        
        viewModel.dataCleared.observe(viewLifecycleOwner, Observer { cleared ->
            if (cleared) {
                Snackbar.make(binding.root, "All data cleared successfully", Snackbar.LENGTH_SHORT).show()
                viewModel.clearDataClearedFlag()
            }
        })
        
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        })
        
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            // Update UI loading state if needed
        })
    }
    
    private fun setupClickListeners() {
        binding.btnSaveProfile.setOnClickListener {
            saveUserProfile()
        }
        
        binding.btnExportAllData.setOnClickListener {
            exportAllData()
        }
        
        binding.btnClearAllData.setOnClickListener {
            showClearDataConfirmation()
        }
    }
    
    private fun loadSettings() {
        // Load user profile
        binding.etEmployeeName.setText(viewModel.getEmployeeName())
        binding.etEmployeeId.setText(viewModel.getEmployeeId())
        
        // Load work settings
        binding.etRequiredHours.setText(viewModel.getRequiredHoursPerDay().toString())
        binding.switchNotifications.isChecked = viewModel.isNotificationsEnabled()
        binding.switchClockOutReminder.isChecked = viewModel.isClockOutReminderEnabled()
    }
    
    private fun saveUserProfile() {
        val employeeName = binding.etEmployeeName.text.toString().trim()
        val employeeId = binding.etEmployeeId.text.toString().trim()
        val requiredHours = binding.etRequiredHours.text.toString().toIntOrNull() ?: 8
        val notificationsEnabled = binding.switchNotifications.isChecked
        val clockOutReminderEnabled = binding.switchClockOutReminder.isChecked
        
        viewModel.saveUserProfile(
            employeeName = employeeName,
            employeeId = employeeId,
            requiredHoursPerDay = requiredHours,
            notificationsEnabled = notificationsEnabled,
            clockOutReminderEnabled = clockOutReminderEnabled
        )
    }
    
    private fun exportAllData() {
        viewModel.exportAllData()
    }
    
    private fun showClearDataConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Clear All Data")
            .setMessage("Are you sure you want to clear all time records? This action cannot be undone.")
            .setPositiveButton("Clear") { _, _ ->
                viewModel.clearAllData()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
