package com.watermelon.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.watermelon.player.ui.theme.WatermelonTheme
import com.watermelon.player.ui.navigation.MainNavGraph

/**
 * [ROOT COMPONENT: MainActivity]
 * * DESIGN PHILOSOPHY: Lean & Clean.
 * This activity is the host for our "Single Activity Architecture."
 * It initializes the UI theme and hands off control to the Navigation Graph.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // FIX: Ensure the window handles "Edge-to-Edge" for immersive 4K playback.
        // This makes the video go behind the status bar and navigation bar.
        
        setContent {
            WatermelonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // THE BRAIN OF UI FLOW: Handles moving between Gallery and Player.
                    MainNavGraph()
                }
            }
        }
    }
}

