package com.watermelon.player.player

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.DefaultLoadControl
import com.watermelon.player.player.loadcontrol.SimpleLoadControl

/**
 * [CORE ENGINE: WatermelonPlayer]
 * * DESIGN PHILOSOPHY: Offline Excellence.
 * This class wraps Media3 (ExoPlayer) to handle massive 4K local files
 * with zero stuttering and professional audio handling.
 */
class WatermelonPlayer(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null

    /**
     * [FUNCTION: initializePlayer]
     * * FIX: Standard ExoPlayer buffers are tuned for Streaming (slow internet).
     * ENHANCEMENT: We re-tune the LoadControl for LOCAL STORAGE.
     * Since the file is on the phone, we can afford a "Fixed Heavy Buffer" 
     * to prevent frame drops in high-bitrate 4K MKV files.
     */
    fun initializePlayer(): ExoPlayer {
        
        // CUSTOM BUFFER LOGIC:
        // We increase the Min Buffer to 30s to ensure the CPU/GPU always 
        // has enough data for 4K decoding, even if the disk is busy.
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                30_000, // minBufferMs: Start playing only after 30s is ready.
                60_000, // maxBufferMs: Keep up to 60s in RAM.
                2_500,  // bufferForPlaybackMs: Fast-start threshold.
                5_000   // bufferForPlaybackAfterRebufferMs.
            ).build()

        exoPlayer = ExoPlayer.Builder(context)
            .setLoadControl(loadControl) // Inject our 4K optimization.
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true // Allow the app to take "Audio Focus" (pauses music players).
            )
            .build()
            
        return exoPlayer!!
    }

    /**
     * [FUNCTION: releasePlayer]
     * CRITICAL: Prevents memory leaks and stops the foreground service.
     */
    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }
}

