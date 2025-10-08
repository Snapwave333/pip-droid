package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.ui.components.VaultBoyIcons
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.PipBoyTab

/**
 * Bottom navigation tab bar for Pip-Boy interface
 */
@Composable
fun PipBoyTabBar(
    currentTab: PipBoyTab,
    onTabSelected: (PipBoyTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.8f))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PipBoyTab.values().forEach { tab ->
            PipBoyTabItem(
                tab = tab,
                isSelected = tab == currentTab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

/**
 * Individual tab item
 */
@Composable
private fun PipBoyTabItem(
    tab: PipBoyTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color.Green.copy(alpha = 0.3f)
    } else {
        Color.Transparent
    }

    val textColor = if (isSelected) {
        Color.Green
    } else {
        Color.Green.copy(alpha = 0.7f)
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = tab.displayName,
            style = PipBoyTypography.labelLarge.copy(
                fontSize = 10.sp,
                letterSpacing = 1.sp
            ),
            color = textColor
        )
    }
}

/**
 * Get display name for each tab
 */
private val PipBoyTab.displayName: String
    get() = when (this) {
        PipBoyTab.HOME -> "CAMP"
        PipBoyTab.STATUS -> "STAT"
        PipBoyTab.INVENTORY -> "INV"
        PipBoyTab.DATA -> "DATA"
        PipBoyTab.MAP -> "MAP"
        PipBoyTab.RADIO -> "RADIO"
        PipBoyTab.ACHIEVEMENTS -> "ACHV"
        PipBoyTab.SETTINGS -> "SETT"
    }

/**
 * Get Vault Boy pose for each tab
 */
private val PipBoyTab.vaultBoyPose: VaultBoyIcons.VaultBoyPose
    get() = when (this) {
        PipBoyTab.HOME -> VaultBoyIcons.VaultBoyPose.EXCITED // Excited for C.A.M.P.
        PipBoyTab.STATUS -> VaultBoyIcons.VaultBoyPose.THINKING // Thinking for stats
        PipBoyTab.INVENTORY -> VaultBoyIcons.VaultBoyPose.HAPPY // Happy for inventory
        PipBoyTab.DATA -> VaultBoyIcons.VaultBoyPose.COOL // Cool for data
        PipBoyTab.MAP -> VaultBoyIcons.VaultBoyPose.SALUTE // Salute for map
        PipBoyTab.RADIO -> VaultBoyIcons.VaultBoyPose.PEACE_SIGN // Peace for radio
        PipBoyTab.ACHIEVEMENTS -> VaultBoyIcons.VaultBoyPose.THUMBS_UP // Thumbs up for achievements
        PipBoyTab.SETTINGS -> VaultBoyIcons.VaultBoyPose.THINKING // Thinking for settings
    }
