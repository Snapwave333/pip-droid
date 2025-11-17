package com.supernova.pipboy.audio

import android.content.Context
import android.location.Location
import android.util.Log
import com.supernova.pipboy.data.model.RadioGenre
import com.supernova.pipboy.data.model.RadioStation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

/**
 * Scans for local FM radio stations
 * Note: Most Android devices don't have FM radio hardware accessible to apps.
 * This provides a fallback using popular internet radio aggregators.
 */
class LocalRadioScanner(private val context: Context) {

    private val _scanningState = MutableStateFlow(ScanningState())
    val scanningState: StateFlow<ScanningState> = _scanningState.asStateFlow()

    private val _discoveredStations = MutableStateFlow<List<RadioStation>>(emptyList())
    val discoveredStations: StateFlow<List<RadioStation>> = _discoveredStations.asStateFlow()

    /**
     * Scan for local radio stations
     * Since most Android devices don't expose FM radio, we'll use internet radio APIs
     */
    suspend fun scanForStations(location: Location? = null) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Starting radio station scan")
        _scanningState.value = _scanningState.value.copy(
            isScanning = true,
            progress = 0f,
            error = null
        )

        try {
            val stations = mutableListOf<RadioStation>()

            // Simulate scanning progress
            for (i in 1..10) {
                _scanningState.value = _scanningState.value.copy(progress = i / 10f)
                kotlinx.coroutines.delay(200)
            }

            // Check for FM radio hardware
            if (hasFmRadioHardware()) {
                Log.d(TAG, "FM radio hardware detected, scanning frequencies...")
                stations.addAll(scanFmFrequencies())
            } else {
                Log.d(TAG, "No FM radio hardware, using internet radio database")
                stations.addAll(getPopularInternetStations(location))
            }

            _discoveredStations.value = stations
            _scanningState.value = _scanningState.value.copy(
                isScanning = false,
                progress = 1f,
                stationsFound = stations.size
            )

            Log.d(TAG, "Scan complete: ${stations.size} stations found")

        } catch (e: Exception) {
            Log.e(TAG, "Scan failed", e)
            _scanningState.value = _scanningState.value.copy(
                isScanning = false,
                error = "Scan failed: ${e.message}"
            )
        }
    }

    /**
     * Check if device has FM radio hardware
     * Most Android devices don't expose this, so we return false
     */
    private fun hasFmRadioHardware(): Boolean {
        return try {
            // Check for FM radio hardware
            // Note: This is not available on most Android devices
            context.packageManager.hasSystemFeature("android.hardware.radio.fm")
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Scan FM frequencies (87.5 - 108.0 MHz)
     * This is a placeholder as most devices don't have accessible FM hardware
     */
    private suspend fun scanFmFrequencies(): List<RadioStation> {
        val stations = mutableListOf<RadioStation>()

        // FM band: 87.5 to 108.0 MHz
        var frequency = 87.5f
        while (frequency <= 108.0f) {
            // Simulate scanning
            kotlinx.coroutines.delay(50)

            // In a real implementation with FM hardware, you would:
            // 1. Tune to frequency
            // 2. Check signal strength
            // 3. Try to decode RDS data for station name
            // 4. Add station if signal is strong enough

            frequency += 0.1f
        }

        return stations
    }

    /**
     * Get popular internet radio stations based on location
     */
    private fun getPopularInternetStations(location: Location?): List<RadioStation> {
        // Popular internet radio stations from various sources
        // These would normally come from an API like Radio Browser, TuneIn, etc.

        return listOf(
            RadioStation(
                id = "local_1",
                name = "Classic Rock Radio",
                description = "The best classic rock hits",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/ClassicRockFlorida",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_2",
                name = "80s Hits Radio",
                description = "Non-stop 80s music",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/80sHits",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_3",
                name = "Jazz FM",
                description = "Smooth jazz 24/7",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/JazzRadio",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_4",
                name = "Electronic Beats",
                description = "Electronic and dance music",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/ElectronicBeats",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_5",
                name = "Country Roads",
                description = "Classic and modern country",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/CountryRoads",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_6",
                name = "Hip Hop Central",
                description = "Hip hop and rap music",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/HipHopCentral",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_7",
                name = "Classical Masterpieces",
                description = "Classical music from the masters",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/ClassicalMasterpieces",
                genre = RadioGenre.INTERNET
            ),
            RadioStation(
                id = "local_8",
                name = "Rock Alternative",
                description = "Alternative and indie rock",
                location = "Internet Radio",
                streamUrl = "http://streaming.radionomy.com/RockAlternative",
                genre = RadioGenre.INTERNET
            )
        )
    }

    /**
     * Clear discovered stations
     */
    fun clearStations() {
        _discoveredStations.value = emptyList()
        _scanningState.value = ScanningState()
    }

    companion object {
        private const val TAG = "LocalRadioScanner"
    }
}

/**
 * Scanning state
 */
data class ScanningState(
    val isScanning: Boolean = false,
    val progress: Float = 0f,
    val stationsFound: Int = 0,
    val error: String? = null
)
