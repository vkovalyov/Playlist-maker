package com.example.playlistmaker.core.data.db.domain.models

import com.example.playlistmaker.searchMusic.domain.models.Track

data class PlaylistWithTracks(
    val playlist: PlayList,
    val tracks: List<Track>
) {
    val tracksCount: Int get() = tracks.size
}