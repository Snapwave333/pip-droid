package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Vault Boy poses and expressions for Pip-Boy interface
 */
enum class VaultBoyPose(
    val asciiArt: String,
    val description: String,
    val color: Color = Color.Green
) {
    THUMBS_UP(
        """
        ╭─╮
        │👍│
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Thumbs up - Approval",
        Color.Green
    ),

    SALUTE(
        """
        ╭─╮
        │🖖│
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Salute - Respect",
        Color.Green.copy(alpha = 0.9f)
    ),

    FACEPALM(
        """
        ╭─╮
        │🤦│
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Facepalm - Frustration",
        Color.Green.copy(alpha = 0.7f)
    ),

    PEACE_SIGN(
        """
        ╭─╮
        │✌️│
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Peace sign - Victory",
        Color.Green.copy(alpha = 0.8f)
    ),

    WORRIED(
        """
        ╭─╮
        │😟│
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Worried - Concern",
        Color.Green.copy(alpha = 0.6f)
    ),

    HAPPY(
        """
        ╭─╮
        │ │  😊
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Happy - Joy",
        Color.Green
    ),

    ANGRY(
        """
        ╭─╮
        │ │  😠
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Angry - Rage",
        Color.Green.copy(alpha = 0.8f)
    ),

    THINKING(
        """
        ╭─╮
        │ │  🤔
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Thinking - Pondering",
        Color.Green.copy(alpha = 0.9f)
    ),

    EXCITED(
        """
        ╭─╮
        │ │  🤩
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Excited - Enthusiasm",
        Color.Green
    ),

    TIRED(
        """
        ╭─╮
        │ │  😴
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Tired - Exhaustion",
        Color.Green.copy(alpha = 0.7f)
    ),

    SCARED(
        """
        ╭─╮
        │ │  😱
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Scared - Fear",
        Color.Green.copy(alpha = 0.6f)
    ),

    COOL(
        """
        ╭─╮
        │ │  😎
        │ │
        │◄►│
        ╰─┴─╯
        """.trimIndent(),
        "Cool - Confidence",
        Color.Green
    )
}

/**
 * Vault Boy icon component for Pip-Boy interface
 */
@Composable
fun VaultBoyIcon(
    pose: VaultBoyPose,
    size: androidx.compose.ui.unit.Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .background(Color.Black, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ASCII art representation (placeholder for actual Vault Boy sprite)
            Text(
                text = pose.asciiArt,
                style = PipBoyTypography.bodyMedium,
                color = pose.color,
                fontSize = 8.sp,
                lineHeight = 10.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Pose description (for accessibility)
            Text(
                text = pose.description,
                style = PipBoyTypography.bodyMedium,
                color = pose.color.copy(alpha = 0.6f),
                fontSize = 6.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

/**
 * Vault Boy icon with animated expression
 */
@Composable
fun AnimatedVaultBoyIcon(
    pose: VaultBoyPose,
    size: androidx.compose.ui.unit.Dp = 48.dp,
    modifier: Modifier = Modifier,
    isAnimated: Boolean = false
) {
    val animatedColor = if (isAnimated) {
        // Simple animation: slight color variation
        pose.color.copy(
            green = (pose.color.green * (0.8f + 0.2f * kotlin.math.sin(System.currentTimeMillis() / 500f))).coerceIn(0f, 1f)
        )
    } else {
        pose.color
    }

    Box(
        modifier = modifier
            .size(size)
            .background(Color.Black, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pose.asciiArt,
                style = PipBoyTypography.bodyMedium,
                color = animatedColor,
                fontSize = 8.sp,
                lineHeight = 10.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = pose.description,
                style = PipBoyTypography.bodyMedium,
                color = animatedColor.copy(alpha = 0.6f),
                fontSize = 6.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

/**
 * Vault Boy achievement icon with glow effect
 */
@Composable
fun VaultBoyAchievementIcon(
    pose: VaultBoyPose,
    size: androidx.compose.ui.unit.Dp = 64.dp,
    modifier: Modifier = Modifier,
    isUnlocked: Boolean = true
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                if (isUnlocked) Color.Black.copy(alpha = 0.8f) else Color.DarkGray.copy(alpha = 0.3f),
                CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isUnlocked) {
            VaultBoyIcon(
                pose = pose,
                size = size - 16.dp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Locked achievement - show silhouette
            Box(
                modifier = Modifier
                    .size(size - 16.dp)
                    .background(Color.Gray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔒",
                    style = PipBoyTypography.displayLarge,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    }
}
