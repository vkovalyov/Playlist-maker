package com.example.playlistmaker.core.data.db.domain.models

data class PlaylistTrack(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?,
    val createdAt: Long
)