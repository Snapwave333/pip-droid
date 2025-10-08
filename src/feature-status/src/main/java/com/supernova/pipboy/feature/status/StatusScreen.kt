package com.supernova.pipboy.feature.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.supernova.pipboy.feature.status.StatusViewModel.StatusUiState
import kotlinx.coroutines.delay

/**
 * Status Screen for the Status feature module
 * Displays comprehensive system status information
 */
@Composable
fun StatusScreen(
    viewModel: StatusViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val systemHealth by viewModel.systemHealth.collectAsStateWithLifecycle()
    val lastUpdate by viewModel.lastUpdate.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        StatusHeader(
            systemHealth = systemHealth,
            lastUpdate = lastUpdate
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on state
        when (uiState) {
            is StatusUiState.Loading -> {
                LoadingIndicator()
            }
            is StatusUiState.Success -> {
                StatusContent(
                    status = (uiState as StatusUiState.Success).status,
                    onRefresh = { viewModel.refreshStatus() },
                    onItemClick = { viewModel.onStatusItemClicked(it) }
                )
            }
            is StatusUiState.Error -> {
                ErrorDisplay(
                    message = (uiState as StatusUiState.Error).message,
                    onRetry = { viewModel.refreshStatus() }
                )
            }
        }
    }
}

/**
 * Status screen header with health indicator
 */
@Composable
private fun StatusHeader(
    systemHealth: Int,
    lastUpdate: Long
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // System Health Indicator
        androidx.compose.material3.Text(
            text = "SYSTEM HEALTH",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Health Progress Bar
        HealthProgressBar(health = systemHealth)

        Spacer(modifier = Modifier.height(8.dp))

        // Last Update Time
        androidx.compose.material3.Text(
            text = "Last Update: ${formatTimestamp(lastUpdate)}",
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = Color.Green.copy(alpha = 0.7f)
        )
    }
}

/**
 * Health progress bar component
 */
@Composable
private fun HealthProgressBar(health: Int) {
    val healthColor = when {
        health >= 80 -> Color.Green
        health >= 60 -> Color.Yellow
        health >= 40 -> Color(1f, 0.5f, 0f) // Orange
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(24.dp)
            .background(Color.DarkGray, androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(health / 100f)
                .height(24.dp)
                .background(healthColor, androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
        )

        androidx.compose.material3.Text(
            text = "$health%",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}

/**
 * Loading indicator
 */
@Composable
private fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Text(
            text = "LOADING SYSTEM STATUS...",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            color = Color.Green
        )
    }
}

/**
 * Main status content
 */
@Composable
private fun StatusContent(
    status: com.supernova.pipboy.data.database.entities.SystemStatusEntity,
    onRefresh: () -> Unit,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // System Overview
        item {
            StatusCard(
                title = "SYSTEM OVERVIEW",
                items = listOf(
                    StatusItem("Battery Level", "${status.batteryLevel}%", "battery"),
                    StatusItem("CPU Usage", "${status.cpuUsage}%", "cpu"),
                    StatusItem("Memory Usage", "${status.memoryUsage}%", "memory"),
                    StatusItem("Temperature", "${status.temperature}°C", "temperature")
                ),
                onItemClick = onItemClick
            )
        }

        // Network Status
        item {
            StatusCard(
                title = "NETWORK STATUS",
                items = listOf(
                    StatusItem("WiFi", if (status.wifiConnected) "Connected" else "Disconnected", "wifi"),
                    StatusItem("Cellular", if (status.cellularConnected) "Connected" else "Disconnected", "cellular"),
                    StatusItem("Bluetooth", if (status.bluetoothEnabled) "Enabled" else "Disabled", "bluetooth")
                ),
                onItemClick = onItemClick
            )
        }

        // Storage Status
        item {
            StatusCard(
                title = "STORAGE STATUS",
                items = listOf(
                    StatusItem("Used", formatBytes(status.storageUsed), "storage_used"),
                    StatusItem("Total", formatBytes(status.storageTotal), "storage_total"),
                    StatusItem("Available", formatBytes(status.storageTotal - status.storageUsed), "storage_available")
                ),
                onItemClick = onItemClick
            )
        }

        // Performance Metrics
        item {
            StatusCard(
                title = "PERFORMANCE METRICS",
                items = listOf(
                    StatusItem("Uptime", formatUptime(status.uptime), "uptime"),
                    StatusItem("Process Count", "${status.processCount}", "processes"),
                    StatusItem("Active Connections", "${status.activeConnections}", "connections")
                ),
                onItemClick = onItemClick
            )
        }

        // Refresh Button
        item {
            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Button(
                onClick = onRefresh,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {
                androidx.compose.material3.Text("REFRESH STATUS")
            }
        }
    }
}

/**
 * Status card component
 */
@Composable
private fun StatusCard(
    title: String,
    items: List<StatusItem>,
    onItemClick: (String) -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            androidx.compose.material3.Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = Color.Green
            )

            Spacer(modifier = Modifier.height(8.dp))

            items.forEach { item ->
                StatusItemRow(
                    item = item,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}

/**
 * Individual status item row
 */
@Composable
private fun StatusItemRow(
    item: StatusItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.Text(
            text = item.label,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = Color.Green.copy(alpha = 0.8f)
        )

        androidx.compose.material3.Text(
            text = item.value,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = Color.Green
        )
    }
}

/**
 * Error display component
 */
@Composable
private fun ErrorDisplay(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.material3.Text(
            text = "SYSTEM ERROR",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        androidx.compose.material3.Text(
            text = message,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = Color.Red.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Button(
            onClick = onRetry,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.Black
            )
        ) {
            androidx.compose.material3.Text("RETRY")
        }
    }
}

/**
 * Data class for status items
 */
private data class StatusItem(
    val label: String,
    val value: String,
    val id: String
)

/**
 * Utility functions
 */
private fun formatTimestamp(timestamp: Long): String {
    val formatter = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(timestamp))
}

private fun formatBytes(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB")
    var size = bytes.toDouble()
    var unitIndex = 0

    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024.0
        unitIndex++
    }

    return String.format("%.1f %s", size, units[unitIndex])
}

private fun formatUptime(uptimeMs: Long): String {
    val totalSeconds = uptimeMs / 1000
    val days = totalSeconds / 86400
    val hours = (totalSeconds % 86400) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        days > 0 -> "${days}d ${hours}h ${minutes}m"
        hours > 0 -> "${hours}h ${minutes}m ${seconds}s"
        minutes > 0 -> "${minutes}m ${seconds}s"
        else -> "${seconds}s"
    }
}
