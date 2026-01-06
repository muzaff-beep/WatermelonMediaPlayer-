
package com.watermelon.player.config

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.watermelon.player.R

object MenaConfig {
    // 1. Regional Languages (Default RTL supported in subtitles)
    val SUPPORTED_LOCALES = listOf("en", "fa", "ar", "ckb", "ur")

    // 2. Business Logic
    const val TRIAL_DAYS = 7
    const val PREMIUM_ENABLED = true

    // 3. Network Beacons (For Internal Proxy Updates)
    // These are Base64 encoded or raw links to clean node lists
    const val PROXY_BEACON_URL = "https://raw.githubusercontent.com/user/repo/main/nodes.txt"
    const val GEO_CHECK_URL = "https://connectivitycheck.gstatic.com/generate_204"

    // 4. Linguistic Rendering (Dynamic Font Selection)
    // You must place these .ttf files in: app/src/main/res/font/
    val PersianFont = FontFamily(Font(R.font.vazir_regular, FontWeight.Normal))
    val UrduFont = FontFamily(Font(R.font.jameel_noori, FontWeight.Normal))
    val ArabicFont = FontFamily(Font(R.font.noto_arabic, FontWeight.Normal))

    fun getFontForLanguage(lang: String): FontFamily {
        return when (lang) {
            "ur" -> UrduFont
            "fa" -> PersianFont
            "ar" -> ArabicFont
            else -> FontFamily.Default
        }
    }
}
