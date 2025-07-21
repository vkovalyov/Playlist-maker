package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.Track


interface SearchHistoryInteractor {
    fun getHistory():  List<Track>
    fun addTrackToHistory(track: Track):  List<Track>
    fun clearHistory()
} 