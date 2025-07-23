package com.example.playlistmaker.searchMusic.domain.models


data class ITunesResponse(
    val resultCount: Int,
    val results: List<Track>,
)