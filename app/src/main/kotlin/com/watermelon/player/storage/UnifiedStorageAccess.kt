package com.watermelon.player.storage

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.watermelon.player.database.VideoEntity

class UnifiedStorageAccess(private val context: Context) {

    /**
     * Scans the device for video files and returns a list of VideoEntities.
     * This logic bypasses folders that the user has toggled "OFF".
     */
    fun fetchLocalVideos(excludedFolders: List<String>): List<VideoEntity> {
        val videoList = mutableListOf<VideoEntity>()
        
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATA // Path to check against exclusion
        )

        val query = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

            while (cursor.moveToNext()) {
                val path = cursor.getString(pathColumn)
                
                // --- INTEGRATED HIDE/SHOW LOGIC ---
                // If the folder path is in our exclusion list, we skip it entirely
                val isExcluded = excludedFolders.any { path.contains(it) }
                if (isExcluded) continue

                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id
                )

                videoList.add(VideoEntity(
                    id = id,
                    title = name,
                    uri = contentUri.toString(),
                    duration = duration,
                    folderPath = path.substringBeforeLast("/")
                ))
            }
        }
        return videoList
    }
}

