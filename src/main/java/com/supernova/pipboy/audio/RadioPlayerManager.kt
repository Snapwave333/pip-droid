package com.supernova.pipboy.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import com.supernova.pipboy.data.model.RadioStation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException

/**
 * Manages radio playback with MediaPlayer
 */
class RadioPlayerManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null

    private val _playerState = MutableStateFlow(RadioPlayerState())
    val playerState: StateFlow<RadioPlayerState> = _playerState.asStateFlow()

    private val _currentStation = MutableStateFlow<RadioStation?>(null)
    val currentStation: StateFlow<RadioStation?> = _currentStation.asStateFlow()

    init {
        setupAudioFocus()
    }

    /**
     * Play a radio station
     */
    fun play(station: RadioStation) {
        Log.d(TAG, "Playing station: ${station.name}")

        // Update state to loading
        _playerState.value = _playerState.value.copy(
            isPlaying = false,
            isLoading = true,
            error = null
        )
        _currentStation.value = station

        try {
            // Release existing player
            release()

            // Request audio focus
            if (!requestAudioFocus()) {
                _playerState.value = _playerState.value.copy(
                    isLoading = false,
                    error = "Failed to get audio focus"
                )
                return
            }

            // Create new MediaPlayer
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                // Set listeners
                setOnPreparedListener {
                    Log.d(TAG, "MediaPlayer prepared, starting playback")
                    it.start()
                    _playerState.value = _playerState.value.copy(
                        isPlaying = true,
                        isLoading = false,
                        error = null
                    )
                }

                setOnErrorListener { _, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        isLoading = false,
                        error = "Playback error: $what"
                    )
                    true
                }

                setOnInfoListener { _, what, extra ->
                    Log.d(TAG, "MediaPlayer info: what=$what, extra=$extra")
                    when (what) {
                        MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                            _playerState.value = _playerState.value.copy(isBuffering = true)
                        }
                        MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                            _playerState.value = _playerState.value.copy(isBuffering = false)
                        }
                    }
                    false
                }

                setOnCompletionListener {
                    Log.d(TAG, "MediaPlayer completed")
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        isLoading = false
                    )
                }

                // Set data source and prepare
                try {
                    setDataSource(context, Uri.parse(station.streamUrl))
                    prepareAsync()
                } catch (e: IOException) {
                    Log.e(TAG, "Failed to set data source", e)
                    _playerState.value = _playerState.value.copy(
                        isLoading = false,
                        error = "Failed to connect to station: ${e.message}"
                    )
                } catch (e: IllegalArgumentException) {
                    Log.e(TAG, "Invalid stream URL", e)
                    _playerState.value = _playerState.value.copy(
                        isLoading = false,
                        error = "Invalid station URL: ${e.message}"
                    )
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to create MediaPlayer", e)
            _playerState.value = _playerState.value.copy(
                isLoading = false,
                error = "Failed to initialize player: ${e.message}"
            )
        }
    }

    /**
     * Pause playback
     */
    fun pause() {
        Log.d(TAG, "Pausing playback")
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _playerState.value = _playerState.value.copy(isPlaying = false)
            }
        }
    }

    /**
     * Resume playback
     */
    fun resume() {
        Log.d(TAG, "Resuming playback")
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                _playerState.value = _playerState.value.copy(isPlaying = true)
            }
        }
    }

    /**
     * Stop playback
     */
    fun stop() {
        Log.d(TAG, "Stopping playback")
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
        }
        _playerState.value = _playerState.value.copy(isPlaying = false, isLoading = false)
        abandonAudioFocus()
    }

    /**
     * Set volume (0.0 to 1.0)
     */
    fun setVolume(volume: Float) {
        val clampedVolume = volume.coerceIn(0f, 1f)
        mediaPlayer?.setVolume(clampedVolume, clampedVolume)
        _playerState.value = _playerState.value.copy(volume = clampedVolume)
    }

    /**
     * Release resources
     */
    fun release() {
        Log.d(TAG, "Releasing MediaPlayer")
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.reset()
            it.release()
            mediaPlayer = null
        }
        _playerState.value = RadioPlayerState()
        abandonAudioFocus()
    }

    /**
     * Setup audio focus handling
     */
    private fun setupAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                .setOnAudioFocusChangeListener { focusChange ->
                    handleAudioFocusChange(focusChange)
                }
                .build()
        }
    }

    /**
     * Request audio focus
     */
    private fun requestAudioFocus(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager.requestAudioFocus(it) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
            } ?: false
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                { focusChange -> handleAudioFocusChange(focusChange) },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    /**
     * Abandon audio focus
     */
    private fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager.abandonAudioFocusRequest(it)
            }
        } else {
            @Suppress("DEPRECATION")
            audioManager.abandonAudioFocus { }
        }
    }

    /**
     * Handle audio focus changes
     */
    private fun handleAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                // Permanent loss of audio focus
                stop()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Temporary loss of audio focus
                pause()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                // Lower volume
                setVolume(0.3f)
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                // Regained audio focus
                if (!_playerState.value.isPlaying) {
                    resume()
                }
                setVolume(_playerState.value.volume)
            }
        }
    }

    companion object {
        private const val TAG = "RadioPlayerManager"
    }
}

/**
 * Radio player state
 */
data class RadioPlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val isBuffering: Boolean = false,
    val volume: Float = 0.75f,
    val error: String? = null
)
