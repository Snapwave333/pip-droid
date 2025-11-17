package com.supernova.pipboy.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Manages runtime permissions for the Pip-Boy launcher
 */
object PermissionManager {

    /**
     * All dangerous permissions that require runtime requests
     */
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECORD_AUDIO
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()

    const val PERMISSION_REQUEST_CODE = 1001

    /**
     * Check if all required permissions are granted
     */
    fun hasAllPermissions(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Get list of permissions that are not granted
     */
    fun getMissingPermissions(context: Context): List<String> {
        return REQUIRED_PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Request all missing permissions
     */
    fun requestMissingPermissions(activity: Activity) {
        val missingPermissions = getMissingPermissions(activity)
        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                missingPermissions.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    /**
     * Handle permission request result
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ): PermissionResult {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return PermissionResult.IRRELEVANT
        }

        val deniedPermissions = mutableListOf<String>()
        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i])
            }
        }

        return when {
            deniedPermissions.isEmpty() -> PermissionResult.ALL_GRANTED
            deniedPermissions.size < permissions.size -> PermissionResult.PARTIALLY_GRANTED
            else -> PermissionResult.ALL_DENIED
        }
    }

    /**
     * Get human-readable permission names for explanation
     */
    fun getPermissionDescription(permission: String): String {
        return when (permission) {
            Manifest.permission.READ_PHONE_STATE -> "Phone State (for network status)"
            Manifest.permission.CAMERA -> "Camera"
            Manifest.permission.READ_EXTERNAL_STORAGE -> "Storage Read"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Storage Write"
            Manifest.permission.ACCESS_FINE_LOCATION -> "Precise Location"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Approximate Location"
            Manifest.permission.READ_CALENDAR -> "Calendar Read"
            Manifest.permission.WRITE_CALENDAR -> "Calendar Write"
            Manifest.permission.READ_SMS -> "SMS Read"
            Manifest.permission.SEND_SMS -> "SMS Send"
            Manifest.permission.RECORD_AUDIO -> "Microphone"
            "android.permission.POST_NOTIFICATIONS" -> "Notifications"
            else -> permission.substringAfterLast('.')
        }
    }
}

enum class PermissionResult {
    ALL_GRANTED,
    PARTIALLY_GRANTED,
    ALL_DENIED,
    IRRELEVANT
}
