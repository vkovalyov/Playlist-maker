package com.example.playlistmaker.searchMusic.domain.repository

import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface MusicRepository {
      fun searchMusic(
        searchType: String,
        searchText: String,
    ): Flow<List<Track>>
}