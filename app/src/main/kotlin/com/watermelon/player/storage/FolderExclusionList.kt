package com.watermelon.player.storage

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages the "Hide/Show" toggles for the user's folders.
 */
class FolderExclusionList(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("watermelon_folders", Context.MODE_PRIVATE)

    fun toggleFolder(path: String, isVisible: Boolean) {
        prefs.edit().putBoolean(path, isVisible).apply()
    }

    fun getExcludedFolders(): List<String> {
        return prefs.all.filter { it.value == false }.keys.toList()
    }
}

