package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Segmented bar component for HP and RADS display
 */
@Composable
fun PipBoySegmentedBar(
    current: Int,
    max: Int,
    segments: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val percentage = (current.toFloat() / max.toFloat()).coerceIn(0f, 1f)
    val filledSegments = (percentage * segments).toInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until segments) {
            val segmentColor = if (i < filledSegments) {
                color
            } else {
                Color.Gray.copy(alpha = 0.3f)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp)
                    .background(
                        color = segmentColor,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}
