package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.data.model.FalloutStations
import com.supernova.pipboy.data.model.RadioStation
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Radio Screen with real audio streaming
 */
@Composable
fun RadioScreenWithPlayer(
    viewModel: MainViewModel,
    initialStationId: String? = null
) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current
    val app = context.applicationContext as PipBoyApplication
    val scope = rememberCoroutineScope()

    // Radio player state
    val playerState by app.radioPlayerManager.playerState.collectAsState()
    val currentStation by app.radioPlayerManager.currentStation.collectAsState()

    // Scanner state
    val scanningState by app.localRadioScanner.scanningState.collectAsState()
    val discoveredStations by app.localRadioScanner.discoveredStations.collectAsState()

    // UI state
    var volume by remember { mutableStateOf(75) }
    var showScanner by remember { mutableStateOf(false) }
    var selectedStationIndex by remember { mutableStateOf(0) }

    // Combined station list (Fallout + Discovered)
    val falloutStations = remember { FalloutStations.getAllStations() }
    val allStations = remember(discoveredStations) {
        falloutStations + discoveredStations
    }

    // Update volume when slider changes
    LaunchedEffect(volume) {
        app.radioPlayerManager.setVolume(volume / 100f)
    }

    // Track achievements
    LaunchedEffect(currentStation) {
        currentStation?.let {
            app.achievementManager.trackEvent(
                AchievementEvent.StationListened(it.name)
            )
        }
    }

    // Cleanup on dispose
    DisposableEffect(Unit) {
        onDispose {
            // Don't release player here, let user control it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RADIO TRANSMISSION",
                style = PipBoyTypography.displayLarge,
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 22.sp
            )

            // Scanner button
            IconButton(
                onClick = {
                    if (!scanningState.isScanning) {
                        scope.launch {
                            app.localRadioScanner.scanForStations()
                        }
                    }
                    showScanner = !showScanner
                }
            ) {
                Icon(
                    imageVector = if (scanningState.isScanning) Icons.Filled.RadioButtonChecked else Icons.Filled.Scanner,
                    contentDescription = "Scan Stations",
                    tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Radio display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .border(2.dp, Color(primaryColor.red, primaryColor.green, primaryColor.blue))
                .background(Color.DarkGray.copy(alpha = 0.2f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Status
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when {
                            playerState.isLoading -> "⚡ CONNECTING"
                            playerState.isBuffering -> "⌛ BUFFERING"
                            playerState.isPlaying -> "► BROADCASTING"
                            else -> "■ STANDBY"
                        },
                        style = PipBoyTypography.labelLarge,
                        color = when {
                            playerState.isPlaying -> Color.Green
                            playerState.isLoading || playerState.isBuffering -> Color.Yellow
                            else -> Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                        },
                        fontSize = 12.sp
                    )
                }

                // Station name
                Text(
                    text = currentStation?.let { "☢ ${it.name}" } ?: "NO STATION SELECTED",
                    style = PipBoyTypography.displayMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 18.sp
                )

                // Description
                currentStation?.let {
                    Text(
                        text = it.description,
                        style = PipBoyTypography.bodyMedium,
                        color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )

                    Text(
                        text = "SOURCE: ${it.location}",
                        style = PipBoyTypography.labelLarge,
                        color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.5f),
                        fontSize = 9.sp
                    )
                }

                // Error message
                playerState.error?.let { error ->
                    Text(
                        text = "⚠ $error",
                        style = PipBoyTypography.labelLarge,
                        color = Color.Red,
                        fontSize = 9.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Previous
                    IconButton(
                        onClick = {
                            selectedStationIndex = (selectedStationIndex - 1 + allStations.size) % allStations.size
                            app.radioPlayerManager.play(allStations[selectedStationIndex])
                        },
                        enabled = !playerState.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SkipPrevious,
                            contentDescription = "Previous",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Play/Pause
                    IconButton(
                        onClick = {
                            if (playerState.isPlaying) {
                                app.radioPlayerManager.pause()
                            } else if (currentStation != null) {
                                app.radioPlayerManager.resume()
                            } else {
                                app.radioPlayerManager.play(allStations[selectedStationIndex])
                            }
                        },
                        enabled = !playerState.isLoading
                    ) {
                        Icon(
                            imageVector = if (playerState.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    // Stop
                    IconButton(
                        onClick = {
                            app.radioPlayerManager.stop()
                        },
                        enabled = playerState.isPlaying || playerState.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Stop,
                            contentDescription = "Stop",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Next
                    IconButton(
                        onClick = {
                            selectedStationIndex = (selectedStationIndex + 1) % allStations.size
                            app.radioPlayerManager.play(allStations[selectedStationIndex])
                        },
                        enabled = !playerState.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SkipNext,
                            contentDescription = "Next",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Volume control
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "VOLUME",
                    style = PipBoyTypography.labelLarge,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 13.sp
                )
                Text(
                    text = "$volume%",
                    style = PipBoyTypography.labelLarge,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 13.sp
                )
            }
            Slider(
                value = volume.toFloat(),
                onValueChange = { volume = it.toInt() },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    activeTrackColor = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    inactiveTrackColor = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.3f)
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scanner status (if scanning)
        if (scanningState.isScanning) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(primaryColor.red, primaryColor.green, primaryColor.blue))
                    .padding(12.dp)
            ) {
                Text(
                    text = "⚡ SCANNING RADIO FREQUENCIES...",
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = scanningState.progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                )
                Text(
                    text = "${(scanningState.progress * 100).toInt()}% Complete",
                    style = PipBoyTypography.labelLarge,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Station list header
        Text(
            text = if (discoveredStations.isNotEmpty()) {
                "ALL FREQUENCIES (${allStations.size} stations)"
            } else {
                "FALLOUT FREQUENCIES (${falloutStations.size} stations)"
            },
            style = PipBoyTypography.displayMedium,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Station list
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color(primaryColor.red, primaryColor.green, primaryColor.blue))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(allStations.size) { index ->
                val station = allStations[index]
                val isSelected = currentStation?.id == station.id
                val isCurrentIndex = index == selectedStationIndex

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            when {
                                isSelected && playerState.isPlaying -> Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                                isCurrentIndex -> Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.3f)
                                else -> Color.Transparent
                            }
                        )
                        .clickable {
                            selectedStationIndex = index
                            app.radioPlayerManager.play(station)
                        }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (station.frequency != null) {
                                "📻 ${station.frequency} MHz - ${station.name}"
                            } else {
                                "☢ ${station.name}"
                            },
                            style = PipBoyTypography.bodyMedium,
                            color = if (isSelected && playerState.isPlaying) Color.Black else Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "${station.location} • ${station.genre.name}",
                            style = PipBoyTypography.labelLarge,
                            color = if (isSelected && playerState.isPlaying)
                                Color.Black.copy(alpha = 0.7f)
                            else
                                Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.6f),
                            fontSize = 9.sp
                        )
                    }
                    if (isSelected && playerState.isPlaying) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Playing",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    if (isSelected && playerState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        // Attribution
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Fallout.FM stations • Internet radio streams • Real audio playback",
            style = PipBoyTypography.labelLarge,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.4f),
            fontSize = 8.sp
        )
    }
}
