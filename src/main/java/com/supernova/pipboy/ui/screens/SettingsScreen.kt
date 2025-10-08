package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.components.RGBColorPicker
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel

/**
 * Pip-Boy Style Settings Screen with RGB Color Picker
 */
@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DISPLAY SETTINGS",
            style = PipBoyTypography.displayLarge,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // RGB Color Picker Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Pip-Boy Color Picker",
                    style = PipBoyTypography.displayMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 18.sp
                )

                Text(
                    text = "Customize your Pip-Boy's signature green glow",
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.8f),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // RGB Color Picker Component
                RGBColorPicker(
                    initialColor = primaryColor,
                    onColorChange = { newColor ->
                        viewModel.updatePrimaryColor(newColor)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Current RGB: (${primaryColor.red}, ${primaryColor.green}, ${primaryColor.blue})",
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
            }
        }

        // CRT Effects Section (placeholder for future expansion)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "CRT Effects",
                    style = PipBoyTypography.displayMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 18.sp
                )

                Text(
                    text = "CRT effects are handled in the main interface",
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
        }

        // Sound Settings Section (placeholder for future expansion)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray.copy(alpha = 0.3f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Sound Settings",
                    style = PipBoyTypography.displayMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                    fontSize = 18.sp
                )

                Text(
                    text = "Sound effects are managed in the main interface",
                    style = PipBoyTypography.bodyMedium,
                    color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Settings saved automatically",
            style = PipBoyTypography.bodyMedium,
            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue).copy(alpha = 0.5f),
            fontSize = 10.sp
        )
    }
}
