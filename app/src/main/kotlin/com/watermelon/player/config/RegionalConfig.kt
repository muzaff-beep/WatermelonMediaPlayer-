package com.watermelon.player.config

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.watermelon.player.R

/**
 * This is the CENTRAL BRAIN for the singular Watermelon Player.
 * It handles the intelligence that adapts the app to different languages and networks.
 */
object RegionalConfig {
    
    // 1. Unified Business Rules (Applies to the singular app everywhere)
    const val TRIAL_DAYS = 7
    const val PREMIUM_ENABLED = true

    // 2. Anti-Censorship Beacons (Only activates if the app detects filtering)
    const val PROXY_BEACON_URL = "https://your-hidden-link.com/nodes.txt"
    const val GEO_CHECK_URL = "https://connectivitycheck.gstatic.com/generate_204"

    // 3. Script-Specific Typography (Ensuring RTL looks professional)
    // Files must be in app/src/main/res/font/
    val PersianFont = FontFamily(Font(R.font.vazir_regular, FontWeight.Normal))
    val UrduFont = FontFamily(Font(R.font.jameel_noori, FontWeight.Normal))

    /**
     * The app calls this to automatically pick the right look for subtitles
     */
    fun getFontForLanguage(langCode: String): FontFamily {
        return when (langCode) {
            "ur" -> UrduFont        // Urdu gets Nastaliq
            "fa", "ar" -> PersianFont // Farsi/Arabic gets Vazir
            else -> FontFamily.Default // English/Others get System Default
        }
    }
}

