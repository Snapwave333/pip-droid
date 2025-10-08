package com.supernova.pipboy.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.ui.components.PipBoyTabBar
import com.supernova.pipboy.ui.navigation.back.PredictiveBackHandler
import com.supernova.pipboy.ui.screens.*
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import com.supernova.pipboy.ui.viewmodel.PipBoyTab
import com.supernova.pipboy.data.repository.SystemRepository

/**
 * Main navigation host for the Pip-Boy interface with Predictive Back support
 */
@Composable
fun PipBoyNavHost(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val app = LocalContext.current.applicationContext as PipBoyApplication
    
    // Track tab visits for Explorer achievement
    LaunchedEffect(currentTab) {
        app.achievementManager.trackEvent(AchievementEvent.TabVisited)
    }

    // Wrap content with Predictive Back Handler
    PredictiveBackHandler(
        currentTab = currentTab,
        onTabChange = { tab -> viewModel.selectTab(tab) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with current tab name and time
            PipBoyHeader(currentTab, viewModel)

            // Main content area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (currentTab) {
                    PipBoyTab.STATUS -> StatusScreen(viewModel)
                    PipBoyTab.INVENTORY -> InventoryScreen(viewModel)
                    PipBoyTab.DATA -> DataScreen(viewModel)
                    PipBoyTab.MAP -> MapScreen(viewModel)
                    PipBoyTab.RADIO -> RadioScreen(viewModel)
                    PipBoyTab.ACHIEVEMENTS -> AchievementsScreen(
                        achievementManager = app.achievementManager
                    )
                }
            }

            // Bottom tab navigation
            PipBoyTabBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        }
    }
}

/**
 * Header displaying current tab and time
 */
@Composable
private fun PipBoyHeader(currentTab: PipBoyTab, viewModel: MainViewModel) {
    val systemStatus by viewModel.systemStatus.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Text(
                text = currentTab.name,
                style = PipBoyTypography.displayMedium,
                color = androidx.compose.ui.graphics.Color.Green
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            androidx.compose.material3.Text(
                text = systemStatus.currentTime,
                style = PipBoyTypography.bodyLarge,
                color = androidx.compose.ui.graphics.Color.Green
            )
        }
    }
}
