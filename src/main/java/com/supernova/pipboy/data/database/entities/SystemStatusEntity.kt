package com.supernova.pipboy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

/**
 * Entity representing system status information
 * Stored locally for historical tracking and offline access
 */
@Entity(
    tableName = "system_status",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["battery_level"]),
        Index(value = ["is_overheating"])
    ]
)
data class SystemStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "battery_level")
    val batteryLevel: Int,

    @ColumnInfo(name = "storage_used_gb")
    val storageUsedGb: Long,

    @ColumnInfo(name = "storage_total_gb")
    val storageTotalGb: Long,

    @ColumnInfo(name = "wifi_connected")
    val wifiConnected: Boolean,

    @ColumnInfo(name = "cellular_connected")
    val cellularConnected: Boolean,

    @ColumnInfo(name = "bluetooth_enabled")
    val bluetoothEnabled: Boolean,

    @ColumnInfo(name = "cpu_usage_percent")
    val cpuUsagePercent: Float,

    @ColumnInfo(name = "temperature_celsius")
    val temperatureCelsius: Float,

    @ColumnInfo(name = "is_overheating")
    val isOverheating: Boolean,

    @ColumnInfo(name = "memory_usage_percent")
    val memoryUsagePercent: Float,

    @ColumnInfo(name = "current_time")
    val currentTime: String,

    @ColumnInfo(name = "uptime_seconds")
    val uptimeSeconds: Long,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
