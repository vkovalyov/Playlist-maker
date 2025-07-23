package com.example.playlistmaker.searchMusic.domain.repository

import com.example.playlistmaker.searchMusic.domain.models.Track


interface MusicRepository {
    fun searchMusic(
        searchType: String,
        searchText: String,
    ): List<Track>
}