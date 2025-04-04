package com.example.playlistmaker.models


data class ITunesResponse(
    val resultCount: Int,
    val results: List<Track>,
)