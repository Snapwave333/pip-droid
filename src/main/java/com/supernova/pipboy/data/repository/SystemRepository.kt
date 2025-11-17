package com.supernova.pipboy.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import androidx.core.content.ContextCompat
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.model.SystemStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Repository for monitoring system status and metrics
 */
class SystemRepository(private val context: Context) {

    private val _systemStatus = MutableStateFlow(SystemStatus())
    val systemStatus: StateFlow<SystemStatus> = _systemStatus

    private val _mediaStatus = MutableStateFlow(MediaStatus())
    val mediaStatus: StateFlow<MediaStatus> = _mediaStatus

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateSystemStatus()
            handler.postDelayed(this, UPDATE_INTERVAL_MS)
        }
    }

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    // CPU usage tracking
    private var lastCpuTotal = 0L
    private var lastCpuIdle = 0L

    init {
        startMonitoring()
    }

    fun startMonitoring() {
        handler.removeCallbacks(updateRunnable)
        handler.post(updateRunnable)
    }

    fun stopMonitoring() {
        handler.removeCallbacks(updateRunnable)
    }

    private fun updateSystemStatus() {
        val batteryLevel = getBatteryLevel()
        val storageInfo = getStorageInfo()
        val connectivityStatus = getConnectivityStatus()
        val cpuUsage = getCpuUsage()
        val temperature = getTemperature()
        val currentTime = timeFormat.format(Date())

        _systemStatus.update { current ->
            current.copy(
                batteryLevel = batteryLevel,
                storageUsed = storageInfo.used,
                storageTotal = storageInfo.total,
                wifiConnected = connectivityStatus.wifi,
                cellularConnected = connectivityStatus.cellular,
                bluetoothEnabled = connectivityStatus.bluetooth,
                cpuUsage = cpuUsage,
                temperature = temperature,
                currentTime = currentTime,
                isOverheating = temperature > OVERHEAT_THRESHOLD
            )
        }

        updateMediaStatus()
    }

    private fun updateMediaStatus() {
        val audioManager = ContextCompat.getSystemService(context, AudioManager::class.java)
        val volume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
        val maxVolume = audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 100

        // This is a simplified media status - in a real implementation,
        // you'd integrate with MediaSession and MediaController
        _mediaStatus.update { current ->
            current.copy(
                volume = volume.toFloat() / maxVolume.toFloat(),
                isPlaying = false, // Would be determined from MediaSession
                currentTrack = "NO TRANSMISSION DETECTED"
            )
        }
    }

    private fun getBatteryLevel(): Int {
        val batteryIntent = ContextCompat.registerReceiver(
            context,
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        return if (level >= 0 && scale > 0) {
            (level * 100 / scale)
        } else {
            100 // Default to full if unable to read
        }
    }

    private fun getStorageInfo(): StorageInfo {
        val totalSpace = context.filesDir.totalSpace
        val freeSpace = context.filesDir.freeSpace
        val usedSpace = totalSpace - freeSpace

        return StorageInfo(
            used = usedSpace / (1024 * 1024 * 1024), // Convert to GB
            total = totalSpace / (1024 * 1024 * 1024)
        )
    }

    private fun getConnectivityStatus(): ConnectivityStatus {
        val connectivityManager = ContextCompat.getSystemService(
            context,
            ConnectivityManager::class.java
        )

        val wifiEnabled = connectivityManager?.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

        val cellularEnabled = connectivityManager?.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false

        val bluetoothEnabled = try {
            val bluetoothAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            bluetoothAdapter?.isEnabled ?: false
        } catch (e: Exception) {
            false
        }

        return ConnectivityStatus(
            wifi = wifiEnabled,
            cellular = cellularEnabled,
            bluetooth = bluetoothEnabled
        )
    }

    /**
     * Get real CPU usage by reading from /proc/stat
     */
    private fun getCpuUsage(): Float {
        return try {
            val reader = RandomAccessFile("/proc/stat", "r")
            val load = reader.readLine()
            reader.close()

            val toks = load.split(" +".toRegex())

            // CPU stats: user, nice, system, idle, iowait, irq, softirq
            val user = toks[1].toLongOrNull() ?: 0L
            val nice = toks[2].toLongOrNull() ?: 0L
            val system = toks[3].toLongOrNull() ?: 0L
            val idle = toks[4].toLongOrNull() ?: 0L
            val iowait = toks[5].toLongOrNull() ?: 0L
            val irq = toks[6].toLongOrNull() ?: 0L
            val softirq = toks[7].toLongOrNull() ?: 0L

            val total = user + nice + system + idle + iowait + irq + softirq

            // Calculate usage percentage
            if (lastCpuTotal != 0L) {
                val totalDiff = total - lastCpuTotal
                val idleDiff = idle - lastCpuIdle

                if (totalDiff > 0) {
                    val usage = ((totalDiff - idleDiff).toFloat() / totalDiff.toFloat()) * 100f
                    lastCpuTotal = total
                    lastCpuIdle = idle
                    usage.coerceIn(0f, 100f)
                } else {
                    0f
                }
            } else {
                // First read, just store values
                lastCpuTotal = total
                lastCpuIdle = idle
                0f
            }
        } catch (e: Exception) {
            // Fallback: return 0 if unable to read
            0f
        }
    }

    /**
     * Get real device temperature from thermal zones
     */
    private fun getTemperature(): Float {
        return try {
            // Try to use PowerManager thermal status (API 29+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                val thermalStatus = powerManager?.currentThermalStatus ?: PowerManager.THERMAL_STATUS_NONE

                // Convert thermal status to approximate temperature
                // THERMAL_STATUS_NONE = 0, LIGHT = 1, MODERATE = 2, SEVERE = 3, CRITICAL = 4, EMERGENCY = 5, SHUTDOWN = 6
                val baseTemp = when (thermalStatus) {
                    PowerManager.THERMAL_STATUS_NONE -> 35.0f
                    PowerManager.THERMAL_STATUS_LIGHT -> 40.0f
                    PowerManager.THERMAL_STATUS_MODERATE -> 45.0f
                    PowerManager.THERMAL_STATUS_SEVERE -> 50.0f
                    PowerManager.THERMAL_STATUS_CRITICAL -> 55.0f
                    PowerManager.THERMAL_STATUS_EMERGENCY -> 60.0f
                    PowerManager.THERMAL_STATUS_SHUTDOWN -> 65.0f
                    else -> 35.0f
                }
                return baseTemp
            }

            // Fallback: Try to read from thermal zone files
            val thermalZones = listOf(
                "/sys/class/thermal/thermal_zone0/temp",
                "/sys/class/thermal/thermal_zone1/temp",
                "/sys/devices/virtual/thermal/thermal_zone0/temp"
            )

            for (zonePath in thermalZones) {
                val file = File(zonePath)
                if (file.exists() && file.canRead()) {
                    val temp = file.readText().trim().toIntOrNull()
                    if (temp != null) {
                        // Temperature is usually in millidegrees Celsius
                        val celsius = if (temp > 1000) temp / 1000f else temp.toFloat()
                        if (celsius in 20f..100f) { // Sanity check
                            return celsius
                        }
                    }
                }
            }

            // Final fallback: read battery temperature
            val batteryIntent = ContextCompat.registerReceiver(
                context,
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            val batteryTemp = batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1
            if (batteryTemp > 0) {
                // Battery temperature is in tenths of degree Celsius
                return batteryTemp / 10f
            }

            // If all methods fail, return a safe default
            35.0f
        } catch (e: Exception) {
            35.0f
        }
    }

    companion object {
        private const val UPDATE_INTERVAL_MS = 5000L // 5 seconds
        private const val OVERHEAT_THRESHOLD = 50.0f // 50°C
    }
}

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
    val isOverheating: Boolean = false
)

data class MediaStatus(
    val volume: Float = 0.5f,
    val isPlaying: Boolean = false,
    val currentTrack: String = "NO TRANSMISSION DETECTED"
)

data class StorageInfo(
    val used: Long,
    val total: Long
)

data class ConnectivityStatus(
    val wifi: Boolean,
    val cellular: Boolean,
    val bluetooth: Boolean
)
