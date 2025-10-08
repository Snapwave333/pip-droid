package com.supernova.pipboy.wear.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.Wearable
import com.supernova.pipboy.wear.data.WearDataManager
import com.supernova.pipboy.wear.services.*
import com.supernova.pipboy.wear.theme.PipBoyWearTheme
import kotlinx.coroutines.launch

/**
 * Main Activity for Pip-Boy Wear OS
 * Provides a simple interface for the watch companion app
 */
class PipBoyWearActivity : ComponentActivity() {

    private lateinit var dataManager: WearDataManager
    private val messageClient by lazy { Wearable.getMessageClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataManager = WearDataManager.getInstance(this)

        setContent {
            PipBoyWearTheme {
                PipBoyWearApp(dataManager)
            }
        }

        // Request initial data sync
        requestDataSync()
    }

    private fun requestDataSync() {
        lifecycleScope.launch {
            try {
                sendMessage(PipBoyWearService.MESSAGE_REQUEST_SYNC)
            } catch (e: Exception) {
                Log.e(TAG, "Error requesting data sync", e)
            }
        }
    }

    private suspend fun sendMessage(path: String) {
        try {
            val nodes = messageClient.connectedNodes.await()
            if (nodes.isNotEmpty()) {
                nodes.forEach { node ->
                    messageClient.sendMessage(node.id, path, null).await()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message to path: $path", e)
        }
    }

    companion object {
        private const val TAG = "PipBoyWearActivity"
    }
}

/**
 * Main Wear App Composable
 */
@Composable
fun PipBoyWearApp(dataManager: WearDataManager) {
    val context = LocalContext.current

    // Observe data changes
    val systemStatus by remember { mutableStateOf(dataManager.getSystemStatus()) }
    val appData by remember { mutableStateOf(dataManager.getAppData()) }
    val userPreferences by remember { mutableStateOf(dataManager.getUserPreferences()) }
    val themeSettings by remember { mutableStateOf(dataManager.getThemeSettings()) }

    Scaffold(
        timeText = {
            TimeText(
                timeSource = TimeTextDefaults.timeSource(TimeTextDefaults.timeFormat())
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            androidx.compose.material3.Text(
                text = "PIP-BOY",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                color = Color.Green,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // System Status
            systemStatus?.let { status ->
                StatusCard(
                    title = "SYSTEM STATUS",
                    status = "OK",
                    details = "Battery: ${status.batteryLevel}% | CPU: ${status.cpuUsage}%"
                )
            } ?: run {
                StatusCard(
                    title = "SYSTEM STATUS",
                    status = "SYNCING...",
                    details = "Waiting for data from phone"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // App Count
            StatusCard(
                title = "INSTALLED APPS",
                status = "${appData.size}",
                details = "Apps synchronized from phone"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Theme Status
            themeSettings?.let { theme ->
                StatusCard(
                    title = "THEME",
                    status = theme.primaryColor.uppercase(),
                    details = "Dark: ${theme.isDarkMode} | Material You: ${theme.useMaterialYou}"
                )
            } ?: run {
                StatusCard(
                    title = "THEME",
                    status = "DEFAULT",
                    details = "Using system theme"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sync Button
            androidx.compose.material3.Button(
                onClick = {
                    // Request manual sync
                    // This would trigger the sync service
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {
                androidx.compose.material3.Text("SYNC DATA")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Settings Button
            androidx.compose.material3.Button(
                onClick = {
                    // Open watch face settings
                    val intent = Intent("com.google.android.wearable.watchface.settings")
                    context.startActivity(intent)
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.Green
                )
            ) {
                androidx.compose.material3.Text("SETTINGS")
            }
        }
    }
}

/**
 * Status Card Component
 */
@Composable
private fun StatusCard(
    title: String,
    status: String,
    details: String
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 4.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
                color = Color.Green.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            androidx.compose.material3.Text(
                text = status,
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                color = Color.Green
            )

            Spacer(modifier = Modifier.height(2.dp))

            androidx.compose.material3.Text(
                text = details,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = Color.Green.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}
