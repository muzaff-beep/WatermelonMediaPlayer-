package com.watermelon.player.player

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.DefaultLoadControl
import com.watermelon.player.player.loadcontrol.SimpleLoadControl

class WatermelonPlayer(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(): ExoPlayer {
        // Optimized LoadControl for 4K local files (fixed buffer logic)
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                30_000, // Min buffer
                60_000, // Max buffer
                2_500,  // Buffer for playback
                5_000   // Buffer for re-play
            ).build()

        exoPlayer = ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
            .build()
            
        return exoPlayer!!
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }
}

