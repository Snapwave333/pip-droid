package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.ui.theme.PipBoyTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Hidden Vault Room - Developer Dashboard
 */
@Composable
fun VaultRoom(
    onDismiss: () -> Unit,
    primaryColor: Color
) {
    val context = LocalContext.current
    val app = context.applicationContext as PipBoyApplication
    val scope = rememberCoroutineScope()

    var showSelfDestruct by remember { mutableStateOf(false) }
    var logs by remember { mutableStateOf("Loading logs...") }

    // Load logs on first display
    LaunchedEffect(Unit) {
        // Simulate loading logs
        delay(500)
        logs = """
            [INFO] Vault-Tec Systems Online
            [INFO] Pip-Boy Interface Initialized
            [DEBUG] Achievement System: 100% Complete
            [DEBUG] Color Picker: Active
            [INFO] Terminal Commands: 50+ Available
            [DEBUG] Quest System: 6 Categories
            [INFO] Radio Stations: 8 Fallout Stations
            [DEBUG] Stats Tracking: Real-time Updates
            [INFO] Vault Door: SECURE
        """.trimIndent()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.9f)
                    .clickable { /* Prevent dismissal when clicking card */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray.copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Text(
                        text = "VAULT-TEC CONTROL ROOM",
                        style = PipBoyTypography.displayLarge,
                        color = Color.Red,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "AUTHORIZED PERSONNEL ONLY",
                        style = PipBoyTypography.bodyMedium,
                        color = Color.Red.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Developer Controls
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black.copy(alpha = 0.8f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "DEVELOPER CONTROLS",
                                style = PipBoyTypography.displayMedium,
                                color = primaryColor,
                                fontSize = 16.sp
                            )

                            // Debug Toggles
                            Text(
                                text = "DEBUG MODE: ENABLED",
                                style = PipBoyTypography.bodyMedium,
                                color = Color.Yellow,
                                fontSize = 12.sp
                            )

                            Text(
                                text = "ACHIEVEMENT TRACKING: ACTIVE",
                                style = PipBoyTypography.bodyMedium,
                                color = Color.Green,
                                fontSize = 12.sp
                            )

                            Text(
                                text = "STATS CALCULATION: REAL-TIME",
                                style = PipBoyTypography.bodyMedium,
                                color = Color.Cyan,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // System Logs
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black.copy(alpha = 0.8f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "SYSTEM LOGS",
                                style = PipBoyTypography.displayMedium,
                                color = primaryColor,
                                fontSize = 16.sp
                            )

                            Text(
                                text = logs,
                                style = PipBoyTypography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Vault Controls
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black.copy(alpha = 0.8f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "VAULT CONTROLS",
                                style = PipBoyTypography.displayMedium,
                                color = primaryColor,
                                fontSize = 16.sp
                            )

                            // Vault Door Override
                            Button(
                                onClick = {
                                    scope.launch {
                                        app.achievementManager.trackEvent(
                                            AchievementEvent.CustomEvent("vault_door_override")
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red.copy(alpha = 0.8f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "⚠️ VAULT DOOR OVERRIDE ⚠️",
                                    style = PipBoyTypography.bodyMedium,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }

                            // Self-Destruct Sequence
                            Button(
                                onClick = { showSelfDestruct = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "🚨 SELF-DESTRUCT SEQUENCE 🚨",
                                    style = PipBoyTypography.bodyMedium,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Close Button
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "CLOSE VAULT ROOM",
                            style = PipBoyTypography.bodyMedium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Self-Destruct Overlay
            if (showSelfDestruct) {
                SelfDestructSequence { showSelfDestruct = false }
            }
        }
    }
}

/**
 * Self-Destruct Sequence Animation
 */
@Composable
fun SelfDestructSequence(
    onComplete: () -> Unit
) {
    var countdown by remember { mutableStateOf(10) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        repeat(10) {
            delay(1000)
            countdown--
        }
        delay(1000)
        onComplete()
    }

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "⚠️ SELF-DESTRUCT INITIATED ⚠️",
                    style = PipBoyTypography.displayLarge,
                    color = Color.White,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = if (countdown > 0) countdown.toString() else "00",
                    style = PipBoyTypography.displayLarge,
                    color = Color.White,
                    fontSize = 48.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (countdown <= 0) {
                    Text(
                        text = "Just kidding.\nVault-Tec loves you.",
                        style = PipBoyTypography.displayMedium,
                        color = Color.Yellow,
                        fontSize = 18.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onComplete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green
                        )
                    ) {
                        Text(
                            text = "DISMISS",
                            style = PipBoyTypography.bodyMedium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
