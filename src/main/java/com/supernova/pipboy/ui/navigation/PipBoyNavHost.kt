package com.supernova.pipboy.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.ui.components.PipBoyTabBar
import com.supernova.pipboy.ui.components.VaultRoom
import com.supernova.pipboy.ui.navigation.back.PredictiveBackHandler
import com.supernova.pipboy.ui.screens.*
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import com.supernova.pipboy.ui.viewmodel.PipBoyTab
import com.supernova.pipboy.data.repository.SystemRepository
import kotlinx.coroutines.delay

/**
 * Main navigation host for the Pip-Boy interface with Predictive Back support
 */
@Composable
fun PipBoyNavHost(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val app = LocalContext.current.applicationContext as PipBoyApplication
    val primaryColor by viewModel.primaryColor.collectAsState()

    // Track tab visits for Explorer achievement
    LaunchedEffect(currentTab) {
        app.achievementManager.trackEvent(AchievementEvent.TabVisited)
    }

    // Hidden Vault Room state
    var showVaultRoom by remember { mutableStateOf(false) }
    var tapCount by remember { mutableStateOf(0) }

    // Triple-tap gesture detector for Vault Room access
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                tapCount++
                if (tapCount >= 3) {
                    showVaultRoom = true
                    tapCount = 0
                }
            }
        )
    }

    // Reset tap count after delay
    LaunchedEffect(tapCount) {
        if (tapCount > 0) {
            delay(2000) // 2 seconds to complete triple tap
            tapCount = 0
        }
    }

    // Wrap content with Predictive Back Handler
    PredictiveBackHandler(
        currentTab = currentTab,
        onTabChange = { tab -> viewModel.selectTab(tab) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(gestureModifier) // Apply triple-tap gesture detection
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            // Header with current tab name and time

            // Main content area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (currentTab) {
                    PipBoyTab.HOME -> CampScreen(viewModel, app.achievementManager)
                    PipBoyTab.STATUS -> StatusScreen(viewModel)
                    PipBoyTab.INVENTORY -> InventoryScreen(viewModel)
                    PipBoyTab.DATA -> DataScreen(viewModel)
                    PipBoyTab.MAP -> MapScreen(viewModel)
                    PipBoyTab.RADIO -> RadioScreenWithPlayer(viewModel)
                    PipBoyTab.ACHIEVEMENTS -> AchievementsScreen(
                        achievementManager = app.achievementManager
                    )
                    PipBoyTab.SETTINGS -> SettingsScreen(viewModel)
                }
            }

            // Bottom tab navigation
            PipBoyTabBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
            }
        }

        // Hidden Vault Room overlay
        if (showVaultRoom) {
            VaultRoom(
                onDismiss = { showVaultRoom = false },
                primaryColor = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
            )
        }
    }
}

