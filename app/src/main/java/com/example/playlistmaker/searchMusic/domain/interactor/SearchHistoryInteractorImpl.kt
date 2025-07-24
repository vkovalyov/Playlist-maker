package com.example.playlistmaker.searchMusic.domain.interactor

import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository

class SearchHistoryInteractorImpl(private val repository: SearchHistoryRepository) :
    SearchHistoryInteractor {
    override fun getHistory(): List<Track> {
        return repository.getHistory()
    }

    override fun saveToHistory(track: Track): List<Track> {
        return repository.saveToHistory(track)
    }

    override fun clearHistory() {
        repository.clearHistory()
    }
}
