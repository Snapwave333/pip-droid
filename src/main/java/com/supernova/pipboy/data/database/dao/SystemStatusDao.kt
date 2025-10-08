package com.supernova.pipboy.data.database.dao

import androidx.room.*
import androidx.room.Query
import com.supernova.pipboy.data.database.entities.SystemStatusEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for system status database operations
 */
@Dao
interface SystemStatusDao {

    // Insert operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemStatus(status: SystemStatusEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemStatuses(statuses: List<SystemStatusEntity>)

    // Update operations
    @Update
    suspend fun updateSystemStatus(status: SystemStatusEntity)

    @Query("UPDATE system_status SET battery_level = :batteryLevel, updated_at = :timestamp WHERE id = :id")
    suspend fun updateBatteryLevel(id: Long, batteryLevel: Int, timestamp: Long)

    // Delete operations
    @Delete
    suspend fun deleteSystemStatus(status: SystemStatusEntity)

    @Query("DELETE FROM system_status WHERE id = :id")
    suspend fun deleteSystemStatusById(id: Long)

    @Query("DELETE FROM system_status WHERE timestamp < :timestamp")
    suspend fun deleteOldSystemStatuses(timestamp: Long)

    @Query("DELETE FROM system_status")
    suspend fun deleteAllSystemStatuses()

    // Query operations
    @Query("SELECT * FROM system_status WHERE id = :id")
    suspend fun getSystemStatusById(id: Long): SystemStatusEntity?

    @Query("SELECT * FROM system_status ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestSystemStatus(): SystemStatusEntity?

    @Query("SELECT * FROM system_status")
    suspend fun getAllSystemStatuses(): List<SystemStatusEntity>

    @Query("SELECT * FROM system_status")
    fun observeAllSystemStatuses(): Flow<List<SystemStatusEntity>>

    @Query("SELECT * FROM system_status WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    suspend fun getSystemStatusesInTimeRange(startTime: Long, endTime: Long): List<SystemStatusEntity>

    @Query("SELECT * FROM system_status WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun observeSystemStatusesInTimeRange(startTime: Long, endTime: Long): Flow<List<SystemStatusEntity>>

    @Query("SELECT * FROM system_status WHERE is_overheating = 1 ORDER BY timestamp DESC")
    suspend fun getOverheatingEvents(): List<SystemStatusEntity>

    @Query("SELECT * FROM system_status WHERE battery_level <= :threshold ORDER BY timestamp DESC")
    suspend fun getLowBatteryEvents(threshold: Int): List<SystemStatusEntity>

    @Query("SELECT COUNT(*) FROM system_status")
    suspend fun getSystemStatusCount(): Int

    @Query("SELECT COUNT(*) FROM system_status WHERE timestamp >= :since")
    suspend fun getSystemStatusCountSince(since: Long): Int

    @Query("SELECT AVG(battery_level) FROM system_status WHERE timestamp >= :since")
    suspend fun getAverageBatteryLevel(since: Long): Double

    @Query("SELECT AVG(cpu_usage_percent) FROM system_status WHERE timestamp >= :since")
    suspend fun getAverageCpuUsage(since: Long): Double

    @Query("SELECT AVG(temperature_celsius) FROM system_status WHERE timestamp >= :since")
    suspend fun getAverageTemperature(since: Long): Double

    @Query("SELECT AVG(memory_usage_percent) FROM system_status WHERE timestamp >= :since")
    suspend fun getAverageMemoryUsage(since: Long): Double

    @Query("SELECT MIN(battery_level) FROM system_status WHERE timestamp >= :since")
    suspend fun getMinimumBatteryLevel(since: Long): Int

    @Query("SELECT MAX(battery_level) FROM system_status WHERE timestamp >= :since")
    suspend fun getMaximumBatteryLevel(since: Long): Int

    @Query("SELECT MIN(temperature_celsius) FROM system_status WHERE timestamp >= :since")
    suspend fun getMinimumTemperature(since: Long): Double

    @Query("SELECT MAX(temperature_celsius) FROM system_status WHERE timestamp >= :since")
    suspend fun getMaximumTemperature(since: Long): Double

    // Time-based queries
    @Query("SELECT * FROM system_status WHERE timestamp >= :timestamp ORDER BY timestamp ASC LIMIT 1")
    suspend fun getFirstSystemStatusAfter(timestamp: Long): SystemStatusEntity?

    @Query("SELECT * FROM system_status WHERE timestamp <= :timestamp ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastSystemStatusBefore(timestamp: Long): SystemStatusEntity?

    @Query("SELECT * FROM system_status WHERE DATE(timestamp / 1000, 'unixepoch') = DATE(:dateInMillis / 1000, 'unixepoch')")
    suspend fun getSystemStatusesForDate(dateInMillis: Long): List<SystemStatusEntity>

    // Hourly aggregation
    @Query("SELECT AVG(battery_level) as avg_battery, AVG(cpu_usage_percent) as avg_cpu, AVG(temperature_celsius) as avg_temp, strftime('%H', timestamp / 1000, 'unixepoch') as hour FROM system_status WHERE timestamp >= :since GROUP BY hour ORDER BY hour")
    suspend fun getHourlySystemStatusAverages(since: Long): List<HourlySystemStatus>

    // Daily aggregation
    @Query("SELECT AVG(battery_level) as avg_battery, AVG(cpu_usage_percent) as avg_cpu, AVG(temperature_celsius) as avg_temp, DATE(timestamp / 1000, 'unixepoch') as date FROM system_status WHERE timestamp >= :since GROUP BY date ORDER BY date")
    suspend fun getDailySystemStatusAverages(since: Long): List<DailySystemStatus>

    // Health monitoring
    @Query("SELECT COUNT(*) FROM system_status WHERE is_overheating = 1 AND timestamp >= :since")
    suspend fun getOverheatingEventCount(since: Long): Int

    @Query("SELECT COUNT(*) FROM system_status WHERE battery_level <= :threshold AND timestamp >= :since")
    suspend fun getLowBatteryEventCount(threshold: Int, since: Long): Int

    @Query("SELECT COUNT(*) FROM system_status WHERE temperature_celsius >= :threshold AND timestamp >= :since")
    suspend fun getHighTemperatureEventCount(threshold: Float, since: Long): Int

    // Performance analysis
    @Query("SELECT * FROM system_status WHERE cpu_usage_percent >= :threshold ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getHighCpuUsageEvents(threshold: Float, limit: Int): List<SystemStatusEntity>

    @Query("SELECT * FROM system_status WHERE memory_usage_percent >= :threshold ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getHighMemoryUsageEvents(threshold: Float, limit: Int): List<SystemStatusEntity>

    // Cleanup operations
    @Query("DELETE FROM system_status WHERE timestamp < :timestamp")
    suspend fun cleanupOldSystemStatuses(timestamp: Long)

    @Query("SELECT MIN(timestamp) FROM system_status")
    suspend fun getOldestSystemStatusTimestamp(): Long?

    @Query("SELECT MAX(timestamp) FROM system_status")
    suspend fun getNewestSystemStatusTimestamp(): Long?
}

/**
 * Hourly aggregated system status data
 */
data class HourlySystemStatus(
    val avgBattery: Double,
    val avgCpu: Double,
    val avgTemp: Double,
    val hour: String
)

/**
 * Daily aggregated system status data
 */
data class DailySystemStatus(
    val avgBattery: Double,
    val avgCpu: Double,
    val avgTemp: Double,
    val date: String
)
