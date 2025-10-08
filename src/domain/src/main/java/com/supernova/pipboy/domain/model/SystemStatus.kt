package com.supernova.pipboy.domain.model

/**
 * Domain model for system status
 * Represents the core business logic entity for system monitoring
 */
data class SystemStatus(
    val id: String,
    val batteryLevel: Int,
    val cpuUsage: Float,
    val memoryUsage: Float,
    val temperature: Float,
    val storageUsed: Long,
    val storageTotal: Long,
    val wifiConnected: Boolean,
    val cellularConnected: Boolean,
    val bluetoothEnabled: Boolean,
    val isOverheating: Boolean,
    val uptime: Long,
    val processCount: Int,
    val activeConnections: Int,
    val currentTime: String,
    val timestamp: Long,
    val healthScore: Int
) {
    /**
     * Check if system is in healthy state
     */
    fun isHealthy(): Boolean = healthScore >= 70

    /**
     * Check if system needs attention
     */
    fun needsAttention(): Boolean = healthScore < 50 || isOverheating

    /**
     * Get health status description
     */
    fun getHealthDescription(): String = when {
        healthScore >= 90 -> "EXCELLENT"
        healthScore >= 80 -> "GOOD"
        healthScore >= 70 -> "FAIR"
        healthScore >= 50 -> "POOR"
        else -> "CRITICAL"
    }

    /**
     * Get storage usage percentage
     */
    fun getStorageUsagePercentage(): Float {
        return if (storageTotal > 0) (storageUsed.toFloat() / storageTotal.toFloat()) * 100 else 0f
    }

    /**
     * Check if battery is low
     */
    fun isBatteryLow(): Boolean = batteryLevel < 20

    /**
     * Check if CPU usage is high
     */
    fun isCpuHigh(): Boolean = cpuUsage > 80

    /**
     * Check if memory usage is high
     */
    fun isMemoryHigh(): Boolean = memoryUsage > 85

    /**
     * Get network status summary
     */
    fun getNetworkSummary(): String {
        val networks = mutableListOf<String>()
        if (wifiConnected) networks.add("WiFi")
        if (cellularConnected) networks.add("Cellular")
        if (bluetoothEnabled) networks.add("Bluetooth")

        return if (networks.isEmpty()) "No connections"
        else networks.joinToString(", ")
    }
}
