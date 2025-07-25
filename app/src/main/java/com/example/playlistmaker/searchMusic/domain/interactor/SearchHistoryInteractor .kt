package com.example.playlistmaker.searchMusic.domain.interactor

import com.example.playlistmaker.searchMusic.domain.models.Track


interface SearchHistoryInteractor {
    fun getHistory():  List<Track>
    fun saveToHistory(track: Track):  List<Track>
    fun clearHistory()
} 