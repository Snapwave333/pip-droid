package com.supernova.pipboy.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.media.session.MediaSessionManager
import android.content.ComponentName
import androidx.core.content.ContextCompat

/**
 * Service for handling media playback controls
 */
class MediaControlService : Service() {

    private lateinit var mediaSessionManager: MediaSessionManager

    override fun onCreate() {
        super.onCreate()
        mediaSessionManager = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Keep service running
        return START_STICKY
    }

    companion object {
        fun startService(context: Context) {
            val intent = Intent(context, MediaControlService::class.java)
            ContextCompat.startForegroundService(context, intent)
        }
    }
}
