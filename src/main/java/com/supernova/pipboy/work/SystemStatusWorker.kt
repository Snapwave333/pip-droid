package com.supernova.pipboy.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
// import com.google.firebase.crashlytics.ktx.crashlytics
// import com.google.firebase.ktx.Firebase
import timber.log.Timber

/**
 * Lightweight background worker to record periodic system status/heartbeat.
 * Keeps it dependency-light to avoid custom WorkerFactory setup.
 */
class SystemStatusWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            // Simple heartbeat
            android.util.Log.d(WORK_NAME, "SystemStatusWorker executed")

            Timber.d("SystemStatusWorker executed successfully")
            Result.success()
        } catch (t: Throwable) {
            Timber.e(t, "SystemStatusWorker failed")
            android.util.Log.e(WORK_NAME, "SystemStatusWorker failed", t)
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME: String = "system_status_worker"
    }
}