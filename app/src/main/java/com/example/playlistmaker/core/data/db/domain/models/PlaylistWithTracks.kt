package com.example.playlistmaker.core.data.db.domain.models

data class PlaylistWithTracks(
    val playlist: PlayList,
    val tracks: List<PlaylistTrack>
) {
    val tracksCount: Int get() = tracks.size
}