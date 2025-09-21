package com.example.playlistmaker.searchMusic.domain.interactor

import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class MusicInteractorImpl(private val repository: MusicRepository) : MusicInteractor {

    override fun searchMusic(
        searchType: String,
        searchText: String,

        ): Flow<List<Track>> {
        return repository.searchMusic(searchType = searchType, searchText = searchText)
    }
}
