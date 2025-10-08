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
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Safe Radio Screen with Fallout.FM stations (no streaming for stability)
 * Displays station information from https://fallout.fm/
 */
@Composable
fun RadioScreen(
    viewModel: MainViewModel,
    initialStationId: String? = null
) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current
    val app = context.applicationContext as PipBoyApplication
    val scope = rememberCoroutineScope()
    
    // Simple state without media player
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableStateOf(75) }
    var currentStationIndex by remember { mutableStateOf(0) }
    var listeningTimeSeconds by remember { mutableStateOf(0) }
    
    // Fallout.FM stations from https://fallout.fm/
    val stations = remember {
        listOf(
            Triple("GALAXY NEWS RADIO", "Three Dog's legendary Fallout 3 station", "Capital Wasteland"),
            Triple("RADIO NEW VEGAS", "Mr. New Vegas from the Mojave", "New Vegas"),
            Triple("DIAMOND CITY RADIO", "Travis Miles from Fallout 4", "Commonwealth"),
            Triple("FALLOUT 4 CLASSICAL", "Classical music from the Commonwealth", "Commonwealth"),
            Triple("FALLOUT.FM MAIN", "All Fallout music in one place", "Worldwide"),
            Triple("FALLOUT 76 GENERAL", "Appalachia radio waves", "Appalachia"),
            Triple("FALLOUT 1 OST", "Original ambient soundtrack", "West Coast"),
            Triple("FALLOUT 2 OST", "The Chosen One's journey", "West Coast")
        )
    }
    
    val currentStation = stations[currentStationIndex]
    
    // Track listening time and achievements
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (isPlaying) {
                delay(1000) // 1 second
                listeningTimeSeconds++
                
                // Track every minute
                if (listeningTimeSeconds % 60 == 0) {
                    app.achievementManager.trackEvent(
                        AchievementEvent.StationListened("any_station")
                    )
                }
            }
        }
    }
    
    // Track station changes
    LaunchedEffect(currentStationIndex) {
        if (currentStationIndex > 0 || isPlaying) {
            app.achievementManager.trackEvent(
                AchievementEvent.StationListened(currentStation.first)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RADIO TRANSMISSION",
            style = PipBoyTypography.displayLarge,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Radio display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .border(2.dp, Color(primaryColor.red, primaryColor.green, primaryColor.blue))
                .background(Color.DarkGray.copy(alpha = 0.2f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Status
                Text(
                    text = if (isPlaying) "► BROADCASTING" else "STANDBY",
                    style = PipBoyTypography.labelLarge,
                    color = if (isPlaying) Color.Green else Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 12.sp
                )
                
                // Station name
                Text(
                    text = "☢ ${currentStation.first}",
                    style = PipBoyTypography.displayMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                // Description
                Text(
                    text = currentStation.second,
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                
                // Location
                Text(
                    text = "SOURCE: ${currentStation.third}",
                    style = PipBoyTypography.labelLarge,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.5f),
                    fontSize = 10.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    IconButton(
                        onClick = { 
                            currentStationIndex = (currentStationIndex - 1 + stations.size) % stations.size
                            isPlaying = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SkipPrevious,
                            contentDescription = "Previous",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { isPlaying = !isPlaying }
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(56.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { 
                            currentStationIndex = (currentStationIndex + 1) % stations.size
                            isPlaying = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SkipNext,
                            contentDescription = "Next",
                            tint = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                
                // Info message
                if (isPlaying) {
                    Text(
                        text = "Note: Connect to fallout.fm for real streaming",
                        style = PipBoyTypography.labelLarge,
                        color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.4f),
                        fontSize = 9.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Volume control
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "VOLUME: $volume%",
                style = PipBoyTypography.labelLarge,
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 14.sp
            )
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
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Station list header
        Text(
            text = "FALLOUT FREQUENCIES",
            style = PipBoyTypography.displayMedium,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
            fontSize = 18.sp,
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
            items(stations.size) { index ->
                val station = stations[index]
                val isSelected = index == currentStationIndex
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) 
                                Color(primaryColor.red, primaryColor.green, primaryColor.blue) 
                            else Color.Transparent
                        )
                        .clickable { 
                            currentStationIndex = index
                            isPlaying = true
                        }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "☢ ${station.first}",
                            style = PipBoyTypography.bodyMedium,
                            color = if (isSelected) Color.Black else Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                            fontSize = 14.sp
                        )
                        Text(
                            text = station.third,
                            style = PipBoyTypography.labelLarge,
                            color = if (isSelected) 
                                Color.Black.copy(alpha = 0.7f) 
                            else 
                                Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                    }
                    if (isSelected && isPlaying) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Playing",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        
        // Attribution
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Station info from fallout.fm • For real streaming visit fallout.fm",
            style = PipBoyTypography.labelLarge,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.4f),
            fontSize = 8.sp
        )
    }
}

