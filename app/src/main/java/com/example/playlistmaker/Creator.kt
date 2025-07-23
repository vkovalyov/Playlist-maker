package com.example.playlistmaker

import com.example.playlistmaker.core.network.RetrofitNetworkClient
import com.example.playlistmaker.searchMusic.data.repository.MusicRepositoryImpl
import com.example.playlistmaker.searchMusic.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.settings.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractorImpl
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractorImpl
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractorImpl
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository
import com.example.playlistmaker.settings.domain.repository.ThemeRepository

object Creator {
    private fun getMusicRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMusicInteractor(): MusicInteractor {
        return MusicInteractorImpl(getMusicRepository())
    }


    private fun getThemeRepository(): ThemeRepository {
        return ThemeRepositoryImpl()
    }

    fun provideThemeInteractor(): ThemeInteractor {
        return ThemeInteractorImpl(getThemeRepository())
    }


    private fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl()
    }

    fun provideSearchHistoryInteractor(): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository())
    }
}