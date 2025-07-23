package com.example.playlistmaker.searchMusic.data.repository

import com.example.playlistmaker.core.data.cache.StorageClient
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository

private const val MAX_HISTORY_SIZE = 10

class SearchHistoryRepositoryImpl(private val storage: StorageClient<ArrayList<Track>>) :
    SearchHistoryRepository {

    override fun getHistory(): List<Track> {
        val tracks = storage.getData() ?: arrayListOf()
        return tracks
    }

    override fun saveToHistory(track: Track): List<Track> {

        val tracks = storage.getData() ?: arrayListOf()

        tracks.remove(track)
        tracks.add(0, track)

        if (tracks.size > MAX_HISTORY_SIZE) {
            tracks.removeAt(tracks.size - 1)
        }

        storage.storeData(tracks)

        return getHistory()
    }

    override fun clearHistory() {
        storage.clear()
    }

}