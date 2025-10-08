package com.supernova.pipboy.dependencyinjection

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import okhttp3.CertificatePinner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for security-related dependencies
 * Provides encryption, secure storage, and security utilities
 */
@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): androidx.security.crypto.EncryptedSharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "pipboy_encrypted_preferences",
            masterKey,
            androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as androidx.security.crypto.EncryptedSharedPreferences
    }

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            // Add certificate pinning for security
            // .add("api.pipboy.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
            .build()
    }

    @Provides
    @Singleton
    fun provideSecurityUtils(@ApplicationContext context: Context): SecurityUtils {
        return SecurityUtils(context)
    }
}

/**
 * Security utilities class
 */
class SecurityUtils(private val context: Context) {

    fun isDeviceSecure(): Boolean {
        // Check if device has PIN, pattern, or password set
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager
        return keyguardManager.isDeviceSecure
    }

    fun isRooted(): Boolean {
        // Check for root access
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        return paths.any { java.io.File(it).exists() }
    }

    fun isEmulator(): Boolean {
        // Check if running on emulator
        return (android.os.Build.FINGERPRINT.startsWith("generic")
                || android.os.Build.FINGERPRINT.startsWith("unknown")
                || android.os.Build.MODEL.contains("google_sdk")
                || android.os.Build.MODEL.contains("Emulator")
                || android.os.Build.MODEL.contains("Android SDK built for x86")
                || android.os.Build.MANUFACTURER.contains("Genymotion")
                || (android.os.Build.BRAND.startsWith("generic") && android.os.Build.DEVICE.startsWith("generic"))
                || "google_sdk" == android.os.Build.PRODUCT)
    }

    fun getSecurityScore(): Int {
        var score = 0

        if (isDeviceSecure()) score += 30
        if (!isRooted()) score += 25
        if (!isEmulator()) score += 20
        if (android.provider.Settings.Global.getInt(context.contentResolver, android.provider.Settings.Global.ADB_ENABLED, 0) == 0) {
            score += 15
        }
        if (android.provider.Settings.Secure.getInt(context.contentResolver, android.provider.Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 0) {
            score += 10
        }

        return score
    }
}
