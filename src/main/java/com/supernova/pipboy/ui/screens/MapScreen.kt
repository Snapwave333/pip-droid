package com.supernova.pipboy.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.supernova.pipboy.ui.components.PipBoyCompass
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.math.*

/**
 * MAP tab - Navigation and location services
 */
@Composable
fun MapScreen(viewModel: MainViewModel) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current

    // Simulated GPS coordinates (in a real app, these would come from GPS)
    var currentLocation by remember {
        mutableStateOf(LocationData(40.7128, -74.0060, "NEW YORK"))
    }

    // Simulated heading (in a real app, this would come from compass sensor)
    var currentHeading by remember { mutableStateOf(0f) }

    // Animate compass rotation
    LaunchedEffect(Unit) {
        while (true) {
            delay(100) // Update 10 times per second
            currentHeading = (currentHeading + 2f) % 360f // Slow rotation for demo
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Map Display Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.8f))
                .border(
                    width = 2.dp,
                    color = primaryColor.toComposeColor(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            PipBoyMapView(
                location = currentLocation,
                heading = currentHeading,
                primaryColor = primaryColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Compass Section
        PipBoyCompass(
            heading = currentHeading,
            primaryColor = primaryColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location Info
        LocationInfo(
            location = currentLocation,
            primaryColor = primaryColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Map Utility Button
        androidx.compose.material3.Button(
            onClick = {
                // Launch default map application
                launchMapApp(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    primaryColor.toComposeColor(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                ),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = primaryColor.toComposeColor()
            )
        ) {
            androidx.compose.material3.Text(
                text = "INITIATE LOCAL MAP UTILITY",
                style = PipBoyTypography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

/**
 * Simplified map view component
 */
@Composable
private fun PipBoyMapView(
    location: LocationData,
    heading: Float,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Simplified map representation
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    Color.DarkGray.copy(alpha = 0.3f),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Grid pattern
            PipBoyMapGrid(primaryColor = primaryColor)

            // Current location indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        primaryColor.toComposeColor(),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.Text(
            text = "VECTOR MAP DISPLAY",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor()
        )

        androidx.compose.material3.Text(
            text = "HIGH CONTRAST MODE",
            style = PipBoyTypography.bodyMedium.copy(fontSize = 12.sp),
            color = primaryColor.toComposeColor().copy(alpha = 0.7f)
        )
    }
}

/**
 * Map grid overlay
 */
@Composable
private fun PipBoyMapGrid(primaryColor: com.supernova.pipboy.data.model.PipBoyColor) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .background(
                Color.Black.copy(alpha = 0.5f),
                shape = androidx.compose.foundation.shape.CircleShape
            )
    ) {
        // Draw grid lines
        for (i in 1..4) {
            // Horizontal lines
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(primaryColor.toComposeColor().copy(alpha = 0.3f))
                    .align(Alignment.TopCenter)
                    .offset(y = (i * 45).dp)
            )

            // Vertical lines
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(primaryColor.toComposeColor().copy(alpha = 0.3f))
                    .align(Alignment.CenterStart)
                    .offset(x = (i * 45).dp)
            )
        }
    }
}

/**
 * Location information display
 */
@Composable
private fun LocationInfo(
    location: LocationData,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        androidx.compose.material3.Text(
            text = "CURRENT LOCATION",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor()
        )

        androidx.compose.material3.Text(
            text = location.name,
            style = PipBoyTypography.displayMedium,
            color = primaryColor.toComposeColor()
        )

        androidx.compose.material3.Text(
            text = "COORDINATES: ${location.latitude}, ${location.longitude}",
            style = PipBoyTypography.bodyMedium,
            color = primaryColor.toComposeColor().copy(alpha = 0.8f)
        )

        androidx.compose.material3.Text(
            text = "ACCURACY: HIGH",
            style = PipBoyTypography.bodyMedium.copy(fontSize = 12.sp),
            color = primaryColor.toComposeColor().copy(alpha = 0.6f)
        )
    }
}

/**
 * Launch the default map application
 */
private fun launchMapApp(context: android.content.Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("geo:0,0?q=")
            setPackage("com.google.android.apps.maps") // Prefer Google Maps
        }
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
        // Fallback to any map application
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("geo:0,0?q=")
            }
            ContextCompat.startActivity(context, intent, null)
        } catch (e2: Exception) {
            // Handle case where no map app is available
        }
    }
}

/**
 * Data class for location information
 */
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val name: String
)
