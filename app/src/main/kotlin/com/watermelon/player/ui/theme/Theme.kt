package com.watermelon.player.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WatermelonColors = lightColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFFFF5252)
)

@Composable
fun WatermelonTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = WatermelonColors, content = content)
}
