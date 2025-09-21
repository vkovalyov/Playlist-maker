package com.example.playlistmaker.searchMusic.domain.interactor

import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface MusicInteractor {
    fun searchMusic(searchType: String, searchText: String) : Flow<List<Track>>

} 