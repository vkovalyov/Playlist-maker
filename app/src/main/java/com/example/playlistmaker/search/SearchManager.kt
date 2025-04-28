package com.example.playlistmaker.search

import com.example.playlistmaker.models.Track
import com.example.playlistmaker.sharedPreferences.SharedPreferencesUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val MAX_HISTORY_SIZE = 10

object SearchManager {
    private val gson = Gson()


    fun getHistory(): List<Track> {
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

    fun addTrackToHistory(track: Track): List<Track> {
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

    fun clearHistory() {
        SharedPreferencesUtil.clearHistory()
    }
}