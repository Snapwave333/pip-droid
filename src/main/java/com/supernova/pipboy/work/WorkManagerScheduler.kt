package com.supernova.pipboy.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
// import com.supernova.pipboy.monitoring.CrashlyticsManager
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Scheduler for managing WorkManager background tasks
 */
@Singleton
class WorkManagerScheduler @Inject constructor(
    private val workManager: WorkManager,
    private val crashlyticsManager: Any // Accept any type to avoid Firebase dependency
) {
    /**
     * Schedule periodic system status updates
     */
    fun scheduleSystemStatusUpdates() {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val systemStatusWorkRequest = PeriodicWorkRequestBuilder<SystemStatusWorker>(
                15, TimeUnit.MINUTES,
                5, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .addTag(SYSTEM_STATUS_TAG)
                .build()

            workManager.enqueueUniquePeriodicWork(
                SystemStatusWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                systemStatusWorkRequest
            )

            // Log to Crashlytics if available
            try {
                (crashlyticsManager as? com.supernova.pipboy.monitoring.CrashlyticsManagerStub)?.logUserAction("Work scheduled: ${SystemStatusWorker.WORK_NAME}")
            } catch (e: Exception) {
                // Ignore if crashlytics not available
            }
            
            Timber.d("Scheduled periodic system status updates")
        } catch (e: Exception) {
            Timber.e(e, "Failed to schedule system status updates")
            try {
                (crashlyticsManager as? com.supernova.pipboy.monitoring.CrashlyticsManagerStub)?.logException(e)
            } catch (ex: Exception) {
                // Ignore if crashlytics not available
            }
        }
    }

    /**
     * Cancel all scheduled work
     */
    fun cancelAllWork() {
        workManager.cancelAllWork()
        Timber.d("Cancelled all scheduled work")
    }

    companion object {
        private const val SYSTEM_STATUS_TAG = "system_status_work"
    }
}