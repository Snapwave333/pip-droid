package com.supernova.pipboy.feature.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supernova.pipboy.data.repository.SystemRepository
import com.supernova.pipboy.monitoring.CrashlyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Status feature module
 * Handles system status data and operations
 */
@HiltViewModel
class StatusViewModel @Inject constructor(
    private val systemRepository: SystemRepository,
    private val crashlyticsManager: CrashlyticsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val uiState: StateFlow<StatusUiState> = _uiState.asStateFlow()

    private val _systemHealth = MutableStateFlow(100)
    val systemHealth: StateFlow<Int> = _systemHealth.asStateFlow()

    private val _lastUpdate = MutableStateFlow(System.currentTimeMillis())
    val lastUpdate: StateFlow<Long> = _lastUpdate

    init {
        loadSystemStatus()
        observeSystemStatus()
    }

    private fun loadSystemStatus() {
        viewModelScope.launch {
            try {
                _uiState.value = StatusUiState.Loading

                val status = systemRepository.getSystemStatus()
                if (status != null) {
                    _uiState.value = StatusUiState.Success(status)
                    _systemHealth.value = calculateSystemHealth(status)
                    _lastUpdate.value = System.currentTimeMillis()

                    // Log to crashlytics
                    crashlyticsManager.logFeatureUsage("status_screen", 0)
                } else {
                    _uiState.value = StatusUiState.Error("Unable to load system status")
                }
            } catch (e: Exception) {
                _uiState.value = StatusUiState.Error("Failed to load system status: ${e.message}")
                crashlyticsManager.recordException(e, "StatusViewModel.loadSystemStatus")
            }
        }
    }

    private fun observeSystemStatus() {
        viewModelScope.launch {
            systemRepository.systemStatusFlow.collect { status ->
                if (status != null) {
                    _uiState.value = StatusUiState.Success(status)
                    _systemHealth.value = calculateSystemHealth(status)
                    _lastUpdate.value = System.currentTimeMillis()
                }
            }
        }
    }

    private fun calculateSystemHealth(status: com.supernova.pipboy.data.database.entities.SystemStatusEntity): Int {
        var health = 100

        // Battery health
        if (status.batteryLevel < 20) health -= 30
        else if (status.batteryLevel < 50) health -= 15

        // CPU health
        if (status.cpuUsage > 80) health -= 20
        else if (status.cpuUsage > 60) health -= 10

        // Memory health
        if (status.memoryUsage > 85) health -= 20
        else if (status.memoryUsage > 70) health -= 10

        // Temperature health
        if (status.temperature > 45) health -= 15
        else if (status.temperature > 40) health -= 5

        return health.coerceIn(0, 100)
    }

    fun refreshStatus() {
        crashlyticsManager.logUserAction("refresh_status")
        loadSystemStatus()
    }

    fun onSystemHealthChanged(health: Int) {
        _systemHealth.value = health
    }

    fun onStatusItemClicked(item: String) {
        crashlyticsManager.logUserAction("status_item_clicked_$item")
    }

    sealed class StatusUiState {
        object Loading : StatusUiState()
        data class Success(val status: com.supernova.pipboy.data.database.entities.SystemStatusEntity) : StatusUiState()
        data class Error(val message: String) : StatusUiState()
    }
}
