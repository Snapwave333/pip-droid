package com.supernova.pipboy.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
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
import java.util.Locale
import kotlin.math.*

/**
 * MAP tab - Navigation and location services with real GPS and compass
 */
@Composable
fun MapScreen(viewModel: MainViewModel) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current

    // Real GPS coordinates
    var currentLocation by remember {
        mutableStateOf(LocationData(0.0, 0.0, "ACQUIRING GPS SIGNAL..."))
    }

    // Real compass heading
    var currentHeading by remember { mutableStateOf(0f) }

    // Setup real GPS location tracking
    DisposableEffect(context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val locationName = getLocationName(context, location.latitude, location.longitude)
                currentLocation = LocationData(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    name = locationName
                )
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // Request location updates if permission granted
        try {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Try GPS first
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000L, // 5 seconds
                        10f,   // 10 meters
                        locationListener
                    )

                    // Get last known location immediately
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
                        val locationName = getLocationName(context, location.latitude, location.longitude)
                        currentLocation = LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            name = locationName
                        )
                    }
                }

                // Fallback to network provider
                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000L,
                        10f,
                        locationListener
                    )

                    // If GPS didn't work, try network location
                    if (currentLocation.name == "ACQUIRING GPS SIGNAL...") {
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.let { location ->
                            val locationName = getLocationName(context, location.latitude, location.longitude)
                            currentLocation = LocationData(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                name = locationName
                            )
                        }
                    }
                }
            } else {
                currentLocation = LocationData(0.0, 0.0, "LOCATION PERMISSION REQUIRED")
            }
        } catch (e: Exception) {
            currentLocation = LocationData(0.0, 0.0, "GPS UNAVAILABLE")
        }

        onDispose {
            try {
                locationManager.removeUpdates(locationListener)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    // Setup real compass sensor
    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                        val rotationMatrix = FloatArray(9)
                        val orientationAngles = FloatArray(3)

                        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)

                        // Convert radians to degrees and normalize
                        val azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                        currentHeading = (azimuth + 360f) % 360f
                    } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
                        // Deprecated but fallback for older devices
                        currentHeading = event.values[0]
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Register sensor listener
        rotationSensor?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        onDispose {
            sensorManager.unregisterListener(sensorListener)
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
 * Get location name from coordinates using Geocoder
 */
private fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            // Try to get city, then locality, then area, then country
            address.locality?.uppercase()
                ?: address.subAdminArea?.uppercase()
                ?: address.adminArea?.uppercase()
                ?: address.countryName?.uppercase()
                ?: "UNKNOWN LOCATION"
        } else {
            "LAT: ${String.format("%.4f", latitude)}, LON: ${String.format("%.4f", longitude)}"
        }
    } catch (e: Exception) {
        "LAT: ${String.format("%.4f", latitude)}, LON: ${String.format("%.4f", longitude)}"
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
