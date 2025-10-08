package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Simplified Theme Settings Component
 * TODO: Implement proper theme settings when complex UI is ready
 */
@Composable
fun ThemeSettings(
    currentColor: PipBoyColor,
    onColorChange: (PipBoyColor) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "THEME SETTINGS",
            style = PipBoyTypography.displayLarge,
            color = Color(currentColor.red, currentColor.green, currentColor.blue),
            fontSize = 24.sp
        )
        
        Text(
            text = "Theme customization",
            style = PipBoyTypography.bodyMedium,
            color = Color(currentColor.red, currentColor.green, currentColor.blue),
            fontSize = 16.sp
        )
        
        Text(
            text = "Settings temporarily simplified",
            style = PipBoyTypography.bodyMedium,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
