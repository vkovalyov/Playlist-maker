package com.example.playlistmaker.searchMusic.domain.repository

import com.example.playlistmaker.searchMusic.domain.models.Track

interface SearchHistoryRepository {
    fun getHistory():  List<Track>
    fun saveToHistory(track: Track):  List<Track>
    fun clearHistory()
}