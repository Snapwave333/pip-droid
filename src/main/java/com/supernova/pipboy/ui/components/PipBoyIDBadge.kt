package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Pip-Boy ID Badge component displaying user information
 */
@Composable
fun PipBoyIDBadge(
    name: String,
    vaultNumber: String,
    rank: String,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Black)
            .border(2.dp, primaryColor)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Header
            Text(
                text = "VAULT-TEC ID",
                style = PipBoyTypography.labelLarge,
                color = primaryColor,
                fontSize = 10.sp
            )

            // Vault Number
            Text(
                text = "VAULT $vaultNumber",
                style = PipBoyTypography.displayMedium,
                color = primaryColor,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Name
            Text(
                text = name.uppercase(),
                style = PipBoyTypography.bodyMedium,
                color = primaryColor,
                fontSize = 12.sp
            )

            // Rank
            Text(
                text = rank.uppercase(),
                style = PipBoyTypography.bodyMedium,
                color = primaryColor.copy(alpha = 0.8f),
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Footer
            Text(
                text = "VAULT DWELLER",
                style = PipBoyTypography.labelLarge,
                color = primaryColor.copy(alpha = 0.6f),
                fontSize = 8.sp
            )
        }
    }
}
