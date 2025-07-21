package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.repository.MusicRepository

class MusicInteractorImpl(private val repository: MusicRepository) : MusicInteractor {

    override fun searchMusic(
        searchType: String,
        searchText: String,
        consumer: MusicInteractor.MusicConsumer
    ) {
        val t = Thread {
            consumer.consume(repository.searchMusic(searchType,searchText))
        }
        t.start()
    }
}
