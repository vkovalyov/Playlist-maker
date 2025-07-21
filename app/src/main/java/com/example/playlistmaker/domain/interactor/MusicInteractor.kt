package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.Track

interface MusicInteractor {
    fun searchMusic(searchType: String, searchText: String, consumer: MusicConsumer)

    interface MusicConsumer {
        fun consume(foundMusic: List<Track>)
    }
} 