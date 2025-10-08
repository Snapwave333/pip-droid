package com.supernova.pipboy.wear.complications

import android.content.Context
import android.graphics.drawable.Icon
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.LongTextComplicationData
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceService
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import com.supernova.pipboy.wear.R
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Pip-Boy Complication Provider for Wear OS
 * Provides complication data from the main Pip-Boy application
 */
class PipBoyComplicationProvider : ComplicationDataSourceService() {

    override fun onComplicationActivated(
        complicationInstanceId: Int,
        type: ComplicationType
    ) {
        super.onComplicationActivated(complicationInstanceId, type)
        // Start listening for data updates from the phone
        startDataListening()
    }

    override fun onComplicationDeactivated(complicationInstanceId: Int) {
        super.onComplicationDeactivated(complicationInstanceId)
        // Stop listening for data updates
        stopDataListening()
    }

    override fun getPreviewData(type: ComplicationType): ComplicationData {
        return when (type) {
            ComplicationType.SHORT_TEXT -> createShortTextData("SYS OK")
            ComplicationType.LONG_TEXT -> createLongTextData("SYSTEM STATUS: OPERATIONAL")
            ComplicationType.RANGED_VALUE -> createRangedValueData(85, 0, 100)
            else -> createShortTextData("PIP-BOY")
        }
    }

    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData {
        return when (request.complicationType) {
            ComplicationType.SHORT_TEXT -> getSystemStatusShort()
            ComplicationType.LONG_TEXT -> getSystemStatusLong()
            ComplicationType.RANGED_VALUE -> getSystemHealthValue()
            else -> getSystemStatusShort()
        }
    }

    private fun createShortTextData(text: String): ShortTextComplicationData {
        return ShortTextComplicationData.Builder(
            text = PlainComplicationText.Builder(text).build(),
            contentDescription = PlainComplicationText.Builder("Pip-Boy System Status").build()
        )
        .setTitle(PlainComplicationText.Builder("STATUS").build())
        .setMonochromaticImage(
            Icon.createWithResource(applicationContext, R.drawable.ic_pipboy_logo)
        )
        .setTapAction(null) // Could launch the wear app
        .build()
    }

    private fun createLongTextData(text: String): LongTextComplicationData {
        return LongTextComplicationData.Builder(
            text = PlainComplicationText.Builder(text).build(),
            contentDescription = PlainComplicationText.Builder("Pip-Boy System Status Details").build()
        )
        .setTitle(PlainComplicationText.Builder("PIP-BOY STATUS").build())
        .setMonochromaticImage(
            Icon.createWithResource(applicationContext, R.drawable.ic_pipboy_logo)
        )
        .build()
    }

    private fun createRangedValueData(value: Int, min: Int, max: Int): ComplicationData {
        return androidx.wear.watchface.complications.data.RangedValueComplicationData.Builder(
            value = value.toFloat(),
            min = min.toFloat(),
            max = max.toFloat(),
            contentDescription = PlainComplicationText.Builder("System Health").build()
        )
        .setText(PlainComplicationText.Builder("$value%").build())
        .setTitle(PlainComplicationText.Builder("HEALTH").build())
        .setMonochromaticImage(
            Icon.createWithResource(applicationContext, R.drawable.ic_pipboy_logo)
        )
        .build()
    }

    private fun getSystemStatusShort(): ShortTextComplicationData {
        val status = getCurrentSystemStatus()
        return createShortTextData(status.shortStatus)
    }

    private fun getSystemStatusLong(): LongTextComplicationData {
        val status = getCurrentSystemStatus()
        return createLongTextData(status.detailedStatus)
    }

    private fun getSystemHealthValue(): ComplicationData {
        val health = getCurrentSystemHealth()
        return createRangedValueData(health, 0, 100)
    }

    private fun getCurrentSystemStatus(): SystemStatusInfo {
        // In a real implementation, this would get data from the phone
        // For now, return mock data
        return SystemStatusInfo(
            shortStatus = "OK",
            detailedStatus = "ALL SYSTEMS OPERATIONAL",
            health = 95,
            timestamp = System.currentTimeMillis()
        )
    }

    private fun getCurrentSystemHealth(): Int {
        // Mock health calculation
        return 85 + (System.currentTimeMillis() % 15).toInt() // 85-100%
    }

    private fun startDataListening() {
        // Start listening for data updates from the phone
        // This would use the Wearable Data Layer API
    }

    private fun stopDataListening() {
        // Stop listening for data updates
    }

    private data class SystemStatusInfo(
        val shortStatus: String,
        val detailedStatus: String,
        val health: Int,
        val timestamp: Long
    )
}
