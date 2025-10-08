package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Holotapes Screen - Terminal and Games
 */
@Composable
fun HolotapesScreen(
    primaryColor: PipBoyColor = PipBoyColor(0, 255, 0),
    selectedGameId: String? = null,
    modifier: Modifier = Modifier
) {
    var showTerminal by remember { mutableStateOf(false) }
    val color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
    
    if (showTerminal) {
        TerminalScreen(
            primaryColor = primaryColor,
            onExit = { showTerminal = false }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "☢ HOLOTAPES",
                style = PipBoyTypography.displayLarge,
                color = color,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Terminal option
            HolotapeCard(
                title = "ROBCO TERMLINK",
                description = "Access terminal command interface",
                color = color,
                onClick = { showTerminal = true }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Placeholder for games
            HolotapeCard(
                title = "RED MENACE",
                description = "Classic arcade platformer",
                color = color.copy(alpha = 0.3f),
                onClick = { /* TODO */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            HolotapeCard(
                title = "ATOMIC COMMAND",
                description = "Defend against missiles",
                color = color.copy(alpha = 0.3f),
                onClick = { /* TODO */ }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            HolotapeCard(
                title = "GROGNAK & RUBY RUINS",
                description = "Text adventure game",
                color = color.copy(alpha = 0.3f),
                onClick = { /* TODO */ }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "Insert holotape to play",
                style = PipBoyTypography.bodyMedium,
                color = color.copy(alpha = 0.5f),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun HolotapeCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, color)
            .clickable(enabled = color.alpha > 0.5f, onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = PipBoyTypography.displayMedium,
            color = color,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = PipBoyTypography.bodyMedium,
            color = color.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
        if (color.alpha <= 0.5f) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "[Coming Soon]",
                style = PipBoyTypography.bodyMedium,
                color = Color.Gray,
                fontSize = 10.sp
            )
        }
    }
}
