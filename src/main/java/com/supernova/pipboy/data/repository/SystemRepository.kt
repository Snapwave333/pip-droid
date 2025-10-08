package com.supernova.pipboy.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.model.SystemStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    private fun getCpuUsage(): Float {
        // Simplified CPU usage - in a real implementation,
        // you'd read from /proc/stat or use Android's built-in monitoring
        return (0..100).random().toFloat() // Placeholder
    }

    private fun getTemperature(): Float {
        // Simplified temperature reading - in a real implementation,
        // you'd use the Thermal API or read from thermal zone files
        return 35.0f + (0..20).random().toFloat() // Placeholder: 35-55°C
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
