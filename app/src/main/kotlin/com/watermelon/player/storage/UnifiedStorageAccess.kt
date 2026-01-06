package com.watermelon.player.storage

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.watermelon.player.database.VideoEntity

/**
 * [DATA LAYER: UnifiedStorageAccess]
 * * ENHANCEMENT: Scoped Storage Compatibility.
 * This handles the complexity of Android 11+ MediaStore queries while 
 * integrating our "Folder Visibility" database filters.
 */
class UnifiedStorageAccess(private val context: Context) {

    /**
     * [FUNCTION: fetchLocalVideos]
     * * FIX: Avoids the "Slow Vault" problem. 
     * Instead of encrypting files to hide them (which is slow), we filter 
     * them out during the scan phase using our Exclusion List.
     * * @param excludedFolders A list of folder paths the user has toggled "OFF".
     */
    fun fetchLocalVideos(excludedFolders: List<String>): List<VideoEntity> {
        val videoList = mutableListOf<VideoEntity>()
        
        // Define which file data we need from the phone.
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATA // The absolute disk path.
        )

        // Query the Android MediaStore Database.
        val query = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC" // Show newest first.
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

            while (cursor.moveToNext()) {
                val path = cursor.getString(pathColumn)
                
                // --- INTEGRATED HIDE/SHOW FILTER ---
                // We check if the current file's path contains any string 
                // from our "Invisible" folders list.
                val isExcluded = excludedFolders.any { path.contains(it) }
                
                // If it's in a hidden folder, we skip adding it to the UI.
                if (isExcluded) continue

                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                
                // Construct the "Playable URI" for Media3.
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                )

                videoList.add(VideoEntity(
                    id = id,
                    title = name,
                    uri = contentUri.toString(),
                    duration = duration,
                    folderPath = path.substringBeforeLast("/") // Get the parent folder.
                ))
            }
        }
        return videoList // Returns only "Visible" media to the Home Screen.
    }
}

