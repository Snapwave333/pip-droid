package com.supernova.pipboy.monitoring

import android.content.Context

/**
 * Stub Crashlytics Manager (Firebase disabled)
 * Provides logging without requiring Firebase configuration
 */
class CrashlyticsManagerStub(
    private val context: Context
) {
    private var isEnabled = true

    fun initialize() {
        android.util.Log.d("CrashlyticsManagerStub", "Crashlytics stub initialized (Firebase disabled)")
    }

    fun setCrashlyticsEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    fun log(message: String) {
        if (isEnabled) {
            android.util.Log.d("Crashlytics", message)
        }
    }

    fun logException(throwable: Throwable) {
        if (isEnabled) {
            android.util.Log.e("Crashlytics", "Exception: ${throwable.message}", throwable)
        }
    }

    fun setCustomKey(key: String, value: String) {
        if (isEnabled) {
            android.util.Log.d("Crashlytics", "Custom key: $key = $value")
        }
    }

    fun setUserId(userId: String) {
        if (isEnabled) {
            android.util.Log.d("Crashlytics", "User ID: $userId")
        }
    }

    fun logUserAction(action: String) {
        if (isEnabled) {
            android.util.Log.d("Crashlytics", "User Action: $action")
        }
    }
}

