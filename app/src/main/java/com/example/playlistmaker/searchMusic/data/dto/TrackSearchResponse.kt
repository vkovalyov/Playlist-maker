package com.example.playlistmaker.searchMusic.data.dto


data class TrackSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>,

    ) : Response()