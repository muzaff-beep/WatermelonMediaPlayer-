package com.watermelon.player.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.watermelon.player.player.WatermelonPlayer
import com.watermelon.player.database.VideoEntity

/**
 * [UI LAYER: PlayerScreen]
 * * CRITICAL INTEGRATION: Bridges Compose with the Media3 Native View.
 * This screen is responsible for the immersive 4K playback experience.
 */
@Composable
fun PlayerScreen(
    video: VideoEntity,
    playerManager: WatermelonPlayer
) {
    val context = LocalContext.current

    // INITIALIZATION: Setup the specialized 4K engine.
    val exoPlayer = remember {
        playerManager.initializePlayer().apply {
            // FIX: Auto-prepare the video URI passed from the Home Screen.
            setMediaItem(androidx.media3.common.MediaItem.fromUri(video.uri))
            prepare()
            playWhenReady = true
        }
    }

    // LIFECYCLE: Clean up the player when the user leaves the screen.
    DisposableEffect(Unit) {
        onDispose {
            playerManager.releasePlayer()
        }
    }

    // THE CANVAS: Uses AndroidView to host the specialized PlayerView.
    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                // ENHANCEMENT: Use "FIT" for cinema aspect ratios.
                resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

