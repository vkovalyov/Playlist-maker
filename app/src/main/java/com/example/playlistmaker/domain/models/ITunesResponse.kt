package com.example.playlistmaker.domain.models


data class ITunesResponse(
    val resultCount: Int,
    val results: List<Track>,
)