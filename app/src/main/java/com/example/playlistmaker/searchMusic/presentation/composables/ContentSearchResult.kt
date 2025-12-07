package com.example.playlistmaker.searchMusic.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.core.ui.TrackListElement
import com.example.playlistmaker.searchMusic.domain.models.Track

@Composable
fun ContentSearchResult(tracks: List<Track>, onClick: (Track) -> Unit) {
    if (tracks.isEmpty()) {
        EmptyResult()
    } else {
        Column {
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(tracks) { track ->
                    TrackListElement(track, onClick)
                }
            }
        }
    }
}