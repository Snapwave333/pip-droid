package com.supernova.pipboy.ui.navigation.back

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.supernova.pipboy.ui.viewmodel.PipBoyTab

/**
 * Simplified Predictive Back Handler for Pip-Boy
 * TODO: Implement proper predictive back when gesture handling is stable
 */
@Composable
fun PredictiveBackHandler(
    currentTab: PipBoyTab,
    onTabChange: (PipBoyTab) -> Unit,
    content: @Composable () -> Unit
) {
    // Simple back handler - just handle back press
    BackHandler {
        // Handle back navigation if needed
        // For now, just do nothing to keep the app running
    }
    
    Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        content()
    }
}
