package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

data class PlaybackState(
    val currentTrackId: Long? = null,
    val title: String = "Select a Folk Melody",
    val artist: String = "Xur-Xandhan Ensemble",
    val instrumentName: String = "Assam Heritage",
    val coverImageUrl: String = "",
    val isPlaying: Boolean = false,
    val currentPositionSeconds: Int = 0,
    val durationSeconds: Int = 180,
    val isShuffled: Boolean = false,
    val isLooping: Boolean = false,
    val volume: Float = 0.85f
)

class AcousticAudioEngine(private val scope: CoroutineScope) {

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private var activeTrackJob: Job? = null
    private var isPlayingAudioLoop = false

    private val sampleRate = 44100

    /**
     * Trigger a single acoustic instrumental note or rhythm hit.
     */
    fun playInstrumentSound(preset: String) {
        scope.launch(Dispatchers.Default) {
            try {
                when (preset.lowercase()) {
                    "pepa" -> playPepaSound()
                    "dhol" -> playDholSound()
                    "gogona" -> playGogonaSound()
                    "tokari" -> playTokariSound()
                    "sutuli" -> playSutuliSound()
                    "bhortaal" -> playBhortaalSound()
                    "bahi" -> playBahiSound()
                    else -> playPepaSound()
                }
            } catch (e: Exception) {
                Log.e("AcousticAudioEngine", "Error generating sound: ${e.message}")
            }
        }
    }

    /**
     * Start/Resume track playback with simulated folk accompaniment or online audio.
     */
    fun playTrack(
        trackId: Long,
        title: String,
        artist: String,
        instrument: String,
        coverUrl: String,
        duration: Int,
        preset: String
    ) {
        _playbackState.value = _playbackState.value.copy(
            currentTrackId = trackId,
            title = title,
            artist = artist,
            instrumentName = instrument,
            coverImageUrl = coverUrl,
            durationSeconds = if (duration > 0) duration else 180,
            isPlaying = true
        )

        startPlaybackLoop(preset)
    }

    fun togglePlayPause() {
        val currentState = _playbackState.value
        if (currentState.isPlaying) {
            _playbackState.value = currentState.copy(isPlaying = false)
            stopPlaybackLoop()
        } else {
            _playbackState.value = currentState.copy(isPlaying = true)
            startPlaybackLoop("pepa")
        }
    }

    fun seekTo(seconds: Int) {
        _playbackState.value = _playbackState.value.copy(
            currentPositionSeconds = seconds.coerceIn(0, _playbackState.value.durationSeconds)
        )
    }

    fun setVolume(volume: Float) {
        _playbackState.value = _playbackState.value.copy(volume = volume.coerceIn(0f, 1f))
    }

    fun toggleShuffle() {
        _playbackState.value = _playbackState.value.copy(isShuffled = !_playbackState.value.isShuffled)
    }

    fun toggleLoop() {
        _playbackState.value = _playbackState.value.copy(isLooping = !_playbackState.value.isLooping)
    }

    private fun startPlaybackLoop(preset: String) {
        activeTrackJob?.cancel()
        isPlayingAudioLoop = true

        activeTrackJob = scope.launch(Dispatchers.Default) {
            var step = 0
            while (isPlayingAudioLoop && _playbackState.value.isPlaying) {
                val currentPos = _playbackState.value.currentPositionSeconds
                val duration = _playbackState.value.durationSeconds

                if (currentPos >= duration) {
                    if (_playbackState.value.isLooping) {
                        _playbackState.value = _playbackState.value.copy(currentPositionSeconds = 0)
                    } else {
                        _playbackState.value = _playbackState.value.copy(isPlaying = false, currentPositionSeconds = 0)
                        break
                    }
                } else {
                    _playbackState.value = _playbackState.value.copy(currentPositionSeconds = currentPos + 1)
                }

                // Periodically produce acoustic rhythm accents corresponding to the song mood
                if (step % 3 == 0) {
                    try {
                        when (preset.lowercase()) {
                            "pepa" -> playPepaMelodyBurst()
                            "dhol" -> playDholSound()
                            "gogona" -> playGogonaSound()
                            "tokari" -> playTokariSound()
                            "sutuli" -> playSutuliSound()
                            "bhortaal" -> playBhortaalSound()
                            "bahi" -> playBahiSound()
                            else -> playPepaMelodyBurst()
                        }
                    } catch (_: Exception) {}
                }

                step++
                delay(1000L)
            }
        }
    }

    fun stopPlaybackLoop() {
        isPlayingAudioLoop = false
        activeTrackJob?.cancel()
    }

    // ==========================================
    // PROCEDURAL ACOUSTIC SYNTHESIZERS
    // ==========================================

    private fun playPepaSound() {
        val numSamples = (sampleRate * 1.2).toInt()
        val samples = ShortArray(numSamples)
        val baseFreq = 523.25 // C5 high reed
        val vibratoFreq = 5.5

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val vibrato = 1.0 + 0.03 * sin(2 * PI * vibratoFreq * t)
            val freq = baseFreq * vibrato
            // Reed timbre (Fundamental + strong odd & rich even harmonics)
            var sample = sin(2 * PI * freq * t) +
                    0.6 * sin(2 * PI * freq * 2 * t) +
                    0.7 * sin(2 * PI * freq * 3 * t) +
                    0.4 * sin(2 * PI * freq * 5 * t)
            // Envelope: Fast attack, sustained reed buzz, gentle release
            val envelope = when {
                t < 0.05 -> t / 0.05
                t > 0.9 -> (1.2 - t) / 0.3
                else -> 1.0
            }
            val amp = (sample / 2.7) * envelope * 24000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playPepaMelodyBurst() {
        val notes = doubleArrayOf(523.25, 587.33, 659.25, 783.99)
        val selectedFreq = notes[Random.nextInt(notes.size)]
        val numSamples = (sampleRate * 0.45).toInt()
        val samples = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val freq = selectedFreq * (1.0 + 0.02 * sin(2 * PI * 6.0 * t))
            val sample = sin(2 * PI * freq * t) + 0.6 * sin(2 * PI * freq * 3 * t)
            val envelope = (1.0 - (t / 0.45)).coerceIn(0.0, 1.0)
            val amp = (sample / 1.6) * envelope * 20000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playDholSound() {
        val numSamples = (sampleRate * 0.5).toInt()
        val samples = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // Pitch drops rapidly simulating a struck taut skin drum
            val freq = 55.0 + 160.0 * exp(-18.0 * t)
            val drumBody = sin(2 * PI * freq * t)
            val rimSnapNoise = (Random.nextDouble(-1.0, 1.0)) * exp(-45.0 * t) * 0.7
            val decay = exp(-7.0 * t)
            val sample = (drumBody + rimSnapNoise) * decay
            val amp = sample * 28000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playGogonaSound() {
        val numSamples = (sampleRate * 0.4).toInt()
        val samples = ShortArray(numSamples)
        val fundamental = 164.81 // E3
        val formant = 880.0 // Mouth resonance

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val reed = if ((t * fundamental % 1.0) < 0.5) 1.0 else -1.0
            val mouthResonance = sin(2 * PI * formant * t)
            val decay = exp(-9.0 * t)
            val sample = (reed * 0.4 + mouthResonance * 0.6) * decay
            val amp = sample * 22000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playTokariSound() {
        val numSamples = (sampleRate * 0.8).toInt()
        val samples = ShortArray(numSamples)
        val freq = 220.0 // A3

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val stringTone = sin(2 * PI * freq * t) +
                    0.5 * sin(2 * PI * freq * 2 * t) +
                    0.25 * sin(2 * PI * freq * 3 * t)
            val woodResonance = sin(2 * PI * 110.0 * t) * 0.3
            val decay = exp(-5.5 * t)
            val sample = (stringTone + woodResonance) * decay
            val amp = sample * 24000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playSutuliSound() {
        val numSamples = (sampleRate * 0.7).toInt()
        val samples = ShortArray(numSamples)
        val freq1 = 784.0 // G5
        val freq2 = 880.0 // A5

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val currentFreq = if (t < 0.3) freq1 else freq2
            val airyNoise = Random.nextDouble(-0.15, 0.15)
            val whistle = sin(2 * PI * currentFreq * t) + airyNoise
            val envelope = when {
                t < 0.08 -> t / 0.08
                t > 0.5 -> (0.7 - t) / 0.2
                else -> 1.0
            }
            val amp = whistle * envelope * 20000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playBhortaalSound() {
        val numSamples = (sampleRate * 1.5).toInt()
        val samples = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            // Cluster of inharmonic bronze frequencies
            val ring = sin(2 * PI * 1040.0 * t) +
                    0.8 * sin(2 * PI * 1420.0 * t) +
                    0.6 * sin(2 * PI * 1950.0 * t) +
                    0.5 * sin(2 * PI * 2800.0 * t)
            val initialClash = Random.nextDouble(-1.0, 1.0) * exp(-40.0 * t)
            val shimmerDecay = exp(-2.5 * t)
            val sample = (ring * 0.6 + initialClash * 0.4) * shimmerDecay
            val amp = sample * 23000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun playBahiSound() {
        val numSamples = (sampleRate * 1.0).toInt()
        val samples = ShortArray(numSamples)
        val freq = 440.0 // A4

        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val vibrato = 1.0 + 0.015 * sin(2 * PI * 5.0 * t)
            val breath = Random.nextDouble(-0.08, 0.08)
            val tone = sin(2 * PI * freq * vibrato * t) +
                    0.2 * sin(2 * PI * freq * 2 * t) + breath
            val envelope = when {
                t < 0.1 -> t / 0.1
                t > 0.7 -> (1.0 - t) / 0.3
                else -> 1.0
            }
            val amp = tone * envelope * 21000 * _playbackState.value.volume
            samples[i] = amp.toInt().coerceIn(-32768, 32767).toShort()
        }
        writeAudio(samples)
    }

    private fun writeAudio(samples: ShortArray) {
        var audioTrack: AudioTrack? = null
        try {
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(samples.size * 2)

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(samples, 0, samples.size)
            audioTrack.play()
        } catch (e: Exception) {
            Log.e("AcousticAudioEngine", "AudioTrack play failed: ${e.message}")
        }
    }
}
