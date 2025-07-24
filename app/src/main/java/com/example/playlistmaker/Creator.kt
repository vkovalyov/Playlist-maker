package com.example.playlistmaker

import android.content.Context
import com.example.playlistmaker.core.data.cache.PrefsStorageClient
import com.example.playlistmaker.core.data.network.RetrofitNetworkClient
import com.example.playlistmaker.searchMusic.data.repository.MusicRepositoryImpl
import com.example.playlistmaker.searchMusic.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.settings.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractorImpl
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractorImpl
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractorImpl
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository
import com.example.playlistmaker.settings.domain.repository.ThemeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val APP_THEME = "app_theme"
private const val HISTORY = "history"

object Creator {
    private fun getMusicRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMusicInteractor(): MusicInteractor {
        return MusicInteractorImpl(getMusicRepository())
    }


    private fun getThemeRepository(context: Context): ThemeRepository {
        return ThemeRepositoryImpl(
            PrefsStorageClient(
                Gson(),
                context,
                APP_THEME,
                object : TypeToken<Boolean>() {}.type
            )
        )
    }

    fun provideThemeInteractor(context: Context): ThemeInteractor {
        return ThemeInteractorImpl(getThemeRepository(context))
    }


    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            PrefsStorageClient(
                Gson(),
                context,
                HISTORY,
                object : TypeToken<ArrayList<Track>>() {}.type,

                )
        )
    }

    fun provideSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))
    }
}