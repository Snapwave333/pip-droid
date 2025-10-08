package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.supernova.pipboy.data.model.PipBoyColor

/**
 * Simplified Monochrome Icon Component
 * TODO: Implement proper icon conversion when complex graphics are ready
 */
@Composable
fun MonochromeIcon(
    drawable: android.graphics.drawable.Drawable,
    color: PipBoyColor,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(Color(color.red, color.green, color.blue))
    )
}
