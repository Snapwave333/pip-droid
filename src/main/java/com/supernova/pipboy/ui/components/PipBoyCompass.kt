package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography
import kotlin.math.*

/**
 * Compass component displaying directional orientation
 */
@Composable
fun PipBoyCompass(
    heading: Float,
    primaryColor: PipBoyColor,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.6f))
            .border(
                width = 1.dp,
                color = primaryColor.toComposeColor().copy(alpha = 0.5f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Text(
            text = "ORIENTATION",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Compass rose
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    Color.DarkGray.copy(alpha = 0.3f),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Compass directions
            CompassRose(primaryColor = primaryColor)

            // Compass needle (rotating)
            CompassNeedle(
                heading = heading,
                primaryColor = primaryColor,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Heading display
        androidx.compose.material3.Text(
            text = "HEADING: ${heading.toInt()}°",
            style = PipBoyTypography.bodyMedium,
            color = primaryColor.toComposeColor()
        )

        // Cardinal direction
        val cardinalDirection = getCardinalDirection(heading)
        androidx.compose.material3.Text(
            text = "DIRECTION: $cardinalDirection",
            style = PipBoyTypography.bodyMedium,
            color = primaryColor.toComposeColor().copy(alpha = 0.8f)
        )
    }
}

/**
 * Compass rose with directional markers
 */
@Composable
private fun CompassRose(primaryColor: PipBoyColor) {
    val directions = listOf("N", "E", "S", "W")
    val angles = listOf(0f, 90f, 180f, 270f)

    directions.forEachIndexed { index, direction ->
        val angle = angles[index]
        val radian = Math.toRadians(angle.toDouble())

        val x = 35 * cos(radian).toFloat()
        val y = 35 * sin(radian).toFloat()

        androidx.compose.material3.Text(
            text = direction,
            style = PipBoyTypography.bodyMedium,
            color = primaryColor.toComposeColor(),
            modifier = Modifier
                .offset(
                    x = 50.dp + x.dp - 8.dp, // Center and adjust for text width
                    y = 50.dp - y.dp - 8.dp  // Center and adjust for text height
                )
                .width(16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

/**
 * Rotating compass needle
 */
@Composable
private fun CompassNeedle(
    heading: Float,
    primaryColor: PipBoyColor,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // North arrow (red)
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(30.dp)
                .background(Color.Red)
                .graphicsLayer(
                    rotationZ = -heading // Negative to rotate opposite to heading
                )
        )

        // South arrow (smaller)
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(20.dp)
                .background(primaryColor.toComposeColor().copy(alpha = 0.7f))
                .graphicsLayer(
                    rotationZ = -heading + 180f // Opposite direction
                )
        )

        // Center dot
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(primaryColor.toComposeColor())
        )
    }
}

/**
 * Convert heading angle to cardinal direction
 */
private fun getCardinalDirection(heading: Float): String {
    val normalizedHeading = ((heading % 360) + 360) % 360

    return when {
        normalizedHeading >= 337.5 || normalizedHeading < 22.5 -> "NORTH"
        normalizedHeading >= 22.5 && normalizedHeading < 67.5 -> "NORTH-EAST"
        normalizedHeading >= 67.5 && normalizedHeading < 112.5 -> "EAST"
        normalizedHeading >= 112.5 && normalizedHeading < 157.5 -> "SOUTH-EAST"
        normalizedHeading >= 157.5 && normalizedHeading < 202.5 -> "SOUTH"
        normalizedHeading >= 202.5 && normalizedHeading < 247.5 -> "SOUTH-WEST"
        normalizedHeading >= 247.5 && normalizedHeading < 292.5 -> "WEST"
        normalizedHeading >= 292.5 && normalizedHeading < 337.5 -> "NORTH-WEST"
        else -> "UNKNOWN"
    }
}
