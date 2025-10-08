package com.supernova.pipboy.domain.usecase

import com.supernova.pipboy.domain.model.SystemStatus
import com.supernova.pipboy.domain.repository.SystemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for getting system status
 * Contains business logic for system status operations
 */
class GetSystemStatusUseCase(
    private val systemRepository: SystemRepository
) {

    /**
     * Get current system status as Flow
     */
    fun execute(): Flow<Result<SystemStatus>> {
        return systemRepository.getSystemStatusStream()
            .map { status ->
                status?.let {
                    // Calculate health score and create updated status
                    val healthScore = calculateHealthScore(it)
                    val updatedStatus = it.copy(healthScore = healthScore)
                    Result.success(updatedStatus)
                } ?: Result.failure(Exception("System status not available"))
            }
    }

    /**
     * Get system status once
     */
    suspend fun executeOnce(): Result<SystemStatus> {
        return try {
            val status = systemRepository.getCurrentSystemStatus()
            status?.let {
                // Calculate health score and create updated status
                val healthScore = calculateHealthScore(it)
                val updatedStatus = it.copy(healthScore = healthScore)
                Result.success(updatedStatus)
            } ?: Result.failure(Exception("System status not available"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get system health summary
     */
    fun getHealthSummary(): Flow<SystemHealthSummary> {
        return execute().map { result ->
            result.fold(
                onSuccess = { status ->
                    SystemHealthSummary(
                        healthScore = status.healthScore,
                        description = status.getHealthDescription(),
                        needsAttention = status.needsAttention(),
                        issues = getSystemIssues(status)
                    )
                },
                onFailure = {
                    SystemHealthSummary(
                        healthScore = 0,
                        description = "ERROR",
                        needsAttention = true,
                        issues = listOf("Unable to retrieve system status")
                    )
                }
            )
        }
    }

    /**
     * Refresh system status
     */
    suspend fun refreshStatus(): Result<Unit> {
        return try {
            systemRepository.refreshSystemStatus()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun calculateHealthScore(status: SystemStatus): Int {
        var score = 100

        // Battery scoring
        when {
            status.batteryLevel < 10 -> score -= 50
            status.batteryLevel < 20 -> score -= 30
            status.batteryLevel < 50 -> score -= 15
        }

        // CPU scoring
        when {
            status.cpuUsage > 90 -> score -= 40
            status.cpuUsage > 80 -> score -= 25
            status.cpuUsage > 60 -> score -= 15
            status.cpuUsage > 40 -> score -= 5
        }

        // Memory scoring
        when {
            status.memoryUsage > 95 -> score -= 40
            status.memoryUsage > 85 -> score -= 25
            status.memoryUsage > 70 -> score -= 15
            status.memoryUsage > 50 -> score -= 5
        }

        // Temperature scoring
        when {
            status.temperature > 50 -> score -= 30
            status.temperature > 45 -> score -= 20
            status.temperature > 40 -> score -= 10
        }

        // Overheating penalty
        if (status.isOverheating) score -= 25

        return score.coerceIn(0, 100)
    }

    private fun getSystemIssues(status: SystemStatus): List<String> {
        val issues = mutableListOf<String>()

        if (status.isBatteryLow()) issues.add("Low battery")
        if (status.isCpuHigh()) issues.add("High CPU usage")
        if (status.isMemoryHigh()) issues.add("High memory usage")
        if (status.isOverheating) issues.add("System overheating")

        if (status.getStorageUsagePercentage() > 90) {
            issues.add("Storage almost full")
        }

        if (!status.wifiConnected && !status.cellularConnected) {
            issues.add("No network connection")
        }

        return issues
    }
}

/**
 * System health summary data class
 */
data class SystemHealthSummary(
    val healthScore: Int,
    val description: String,
    val needsAttention: Boolean,
    val issues: List<String>
)
