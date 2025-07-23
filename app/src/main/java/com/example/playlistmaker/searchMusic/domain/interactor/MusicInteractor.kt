package com.example.playlistmaker.searchMusic.domain.interactor

import com.example.playlistmaker.searchMusic.domain.models.Track

interface MusicInteractor {
    fun searchMusic(searchType: String, searchText: String, consumer: MusicConsumer)

    interface MusicConsumer {
        fun consume(foundMusic: List<Track>)
    }
} 