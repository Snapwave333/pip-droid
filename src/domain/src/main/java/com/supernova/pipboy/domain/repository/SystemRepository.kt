package com.supernova.pipboy.domain.repository

import com.supernova.pipboy.domain.model.SystemStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for system status operations
 * Defines the contract for data access operations
 */
interface SystemRepository {

    /**
     * Get system status as a stream
     */
    fun getSystemStatusStream(): Flow<SystemStatus?>

    /**
     * Get current system status
     */
    suspend fun getCurrentSystemStatus(): SystemStatus?

    /**
     * Refresh system status from data source
     */
    suspend fun refreshSystemStatus()

    /**
     * Get system status by ID
     */
    suspend fun getSystemStatusById(id: String): SystemStatus?

    /**
     * Save system status
     */
    suspend fun saveSystemStatus(status: SystemStatus)

    /**
     * Delete system status
     */
    suspend fun deleteSystemStatus(id: String)

    /**
     * Get system status history
     */
    fun getSystemStatusHistory(limit: Int = 10): Flow<List<SystemStatus>>

    /**
     * Get average system metrics over time period
     */
    suspend fun getAverageMetrics(
        startTime: Long,
        endTime: Long
    ): Map<String, Float>

    /**
     * Check if system status is stale
     */
    suspend fun isStatusStale(thresholdMs: Long = 300000): Boolean

    /**
     * Get system status trends
     */
    fun getStatusTrends(): Flow<Map<String, List<Float>>>
}
