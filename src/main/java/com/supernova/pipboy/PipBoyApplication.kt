package com.supernova.pipboy

import android.app.Application
import androidx.startup.AppInitializer
import com.supernova.pipboy.dependencyinjection.SecurityUtils
import com.supernova.pipboy.services.MediaControlService
import com.supernova.pipboy.monitoring.CrashlyticsManagerStub
// import com.supernova.pipboy.monitoring.AdvancedCrashReporting
// import com.supernova.pipboy.monitoring.CrashlyticsManager
// import com.google.firebase.Firebase
import com.supernova.pipboy.work.WorkManagerScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PipBoyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Temporarily create instances manually without Hilt
    lateinit var systemRepository: com.supernova.pipboy.data.repository.SystemRepository
    lateinit var appRepository: com.supernova.pipboy.data.repository.AppRepository
    lateinit var preferences: com.supernova.pipboy.data.preferences.PipBoyPreferences
    private lateinit var securityUtils: SecurityUtils
    private lateinit var timberTree: Timber.Tree
    private lateinit var crashlyticsManager: CrashlyticsManagerStub
    // private lateinit var advancedCrashReporting: AdvancedCrashReporting
    private lateinit var workManagerScheduler: WorkManagerScheduler

    override fun onCreate() {
        super.onCreate()

        // Initialize dependencies manually
        initializeDependencies()

        // Initialize Timber for logging
        Timber.plant(timberTree)

        // Perform security checks
        performSecurityChecks()

        // Start media control service (optional)
        try {
            MediaControlService.startService(this)
        } catch (e: Exception) {
            android.util.Log.w("PipBoyApplication", "Failed to start MediaControlService: ${e.message}")
        }

        // Initialize monitoring and crash reporting (optional)
        try {
            if (::crashlyticsManager.isInitialized) {
                crashlyticsManager.initialize()
            }
            if (::workManagerScheduler.isInitialized) {
                workManagerScheduler.scheduleSystemStatusUpdates()
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize monitoring components")
        }

        // Load initial data
        applicationScope.launch {
            try {
                appRepository.refreshAppList()
                systemRepository.startMonitoring()

                Timber.d("Pip-Boy application initialized successfully")
            } catch (e: Exception) {
                Timber.e(e, "Error during application initialization")
            }
        }
    }

    private fun initializeDependencies() {
        try {
            // Create instances manually
            systemRepository = com.supernova.pipboy.data.repository.SystemRepository(this)
            preferences = com.supernova.pipboy.data.preferences.PipBoyPreferences(this)
            appRepository = com.supernova.pipboy.data.repository.AppRepository(this, preferences)
            securityUtils = SecurityUtils(this)
            timberTree = if (com.supernova.pipboy.BuildConfig.DEBUG) {
                Timber.DebugTree()
            } else {
                object : Timber.Tree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        // Production logging
                    }
                }
            }
            
            // Initialize Crashlytics stub (Firebase disabled)
            crashlyticsManager = CrashlyticsManagerStub(this)
            workManagerScheduler = WorkManagerScheduler(androidx.work.WorkManager.getInstance(this), crashlyticsManager)
        } catch (e: Exception) {
            android.util.Log.e("PipBoyApplication", "Critical error during initialization", e)
            throw e
        }
    }

    private fun performSecurityChecks() {
        val securityScore = securityUtils.getSecurityScore()

        Timber.i("Security Score: $securityScore/100")

        if (securityUtils.isRooted()) {
            Timber.w("Device appears to be rooted")
        }

        if (securityUtils.isEmulator()) {
            Timber.i("Running on emulator")
        }

        if (!securityUtils.isDeviceSecure()) {
            Timber.w("Device does not have secure lock screen")
        }
    }
}
