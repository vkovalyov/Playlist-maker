package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.Track


interface MusicRepository {
    fun searchMusic(
        searchType: String,
        searchText: String,
    ): List<Track>
}