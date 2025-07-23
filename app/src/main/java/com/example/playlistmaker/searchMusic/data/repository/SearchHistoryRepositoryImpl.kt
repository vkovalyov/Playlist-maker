package com.example.playlistmaker.searchMusic.data.repository

import com.example.playlistmaker.core.cache.SharedPreferencesUtil
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val MAX_HISTORY_SIZE = 10

class SearchHistoryRepositoryImpl() : SearchHistoryRepository {
    private val gson = Gson()

    override fun getHistory(): List<Track> {
        val historyString = SharedPreferencesUtil.getHistory()
        val tracks: MutableList<Track>

        if (historyString.isNullOrEmpty()) {
            tracks = mutableListOf()
        } else {
            val type = object : TypeToken<List<Track>>() {}.type

            tracks = gson.fromJson(historyString, type)
        }
        return tracks
    }

    override fun addTrackToHistory(track: Track): List<Track> {
        val historyString = SharedPreferencesUtil.getHistory()

        val tracks: MutableList<Track>

        if (historyString.isNullOrEmpty()) {
            tracks = mutableListOf()
        } else {
            val type = object : TypeToken<List<Track>>() {}.type

            tracks = gson.fromJson(historyString, type)
        }


        tracks.remove(track)
        tracks.add(0, track)

        if (tracks.size > MAX_HISTORY_SIZE) {
            tracks.removeAt(tracks.size - 1)
        }

        val json = gson.toJson(tracks)
        SharedPreferencesUtil.setHistory(json)

        return getHistory()
    }

    override fun clearHistory() {
        SharedPreferencesUtil.clearHistory()
    }

}