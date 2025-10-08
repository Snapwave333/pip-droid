package com.supernova.pipboy.data.model

/**
 * Data class representing system status information
 */
data class SystemStatus(
    val batteryLevel: Int = 100,
    val storageUsed: Long = 0,
    val storageTotal: Long = 1000,
    val wifiConnected: Boolean = true,
    val cellularConnected: Boolean = false,
    val bluetoothEnabled: Boolean = false,
    val cpuUsage: Float = 0.0f,
    val temperature: Float = 35.0f,
    val currentTime: String = "00:00",
    val isOverheating: Boolean = false,
    val memoryUsage: Float = 0.0f,
    val uptime: Long = 0,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Check if system is in healthy state
     */
    fun isHealthy(): Boolean = !isOverheating && batteryLevel > 20 && cpuUsage < 80

    /**
     * Check if system needs attention
     */
    fun needsAttention(): Boolean = isOverheating || batteryLevel < 20 || cpuUsage > 80

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
}
