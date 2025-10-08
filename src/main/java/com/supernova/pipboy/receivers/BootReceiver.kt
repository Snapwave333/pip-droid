package com.supernova.pipboy.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.supernova.pipboy.services.MediaControlService

/**
 * Receiver for boot completed events
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                // Restart services after boot
                context?.let { MediaControlService.startService(it) }
            }
        }
    }
}
