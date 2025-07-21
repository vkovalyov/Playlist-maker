package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.Track

interface SearchHistoryRepository {
    fun getHistory():  List<Track>
    fun addTrackToHistory(track: Track):  List<Track>
    fun clearHistory()
}