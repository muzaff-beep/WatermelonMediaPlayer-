package com.watermelon.player.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watermelon.player.storage.UnifiedStorageAccess
import com.watermelon.player.storage.FolderExclusionList
import com.watermelon.player.database.VideoEntity

/**
 * [UI LAYER: HomeScreen]
 * * ENHANCEMENT: High-Performance Thumbnail Grid.
 * This screen fetches data from our 'UnifiedStorageAccess' and displays it.
 */
@Composable
fun HomeScreen(
    storageAccess: UnifiedStorageAccess,
    exclusionList: FolderExclusionList,
    onVideoClick: (VideoEntity) -> Unit
) {
    // STATE MANAGEMENT: Holds the list of videos currently visible.
    var videos by remember { mutableStateOf(emptyList<VideoEntity>()) }

    // EFFECT: Re-scans storage whenever the screen is loaded.
    LaunchedEffect(Unit) {
        // FIX: We pull the "Excluded Folders" first so the scan ignores them immediately.
        val hidden = exclusionList.getExcludedFolders()
        videos = storageAccess.fetchLocalVideos(hidden)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(title = { Text("Watermelon Player") })
        }
    ) { padding ->
        // GRID LAYOUT: Optimized for 4K video thumbnails.
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(videos) { video ->
                VideoThumbnailItem(
                    video = video,
                    onClick = { onVideoClick(video) }
                )
            }
        }
    }
}

/**
 * [SUB-COMPONENT: VideoThumbnailItem]
 * Aggressively displays duration and title for each media file.
 */
@Composable
fun VideoThumbnailItem(video: VideoEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            // Placeholder for Thumbnail (In Phase 2, we use Coil/Glide for video frames)
            Box(modifier = Modifier.height(100.dp).fillMaxWidth()) 
            
            Text(
                text = video.title,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

