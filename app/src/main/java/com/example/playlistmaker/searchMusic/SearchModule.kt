package com.example.playlistmaker.searchMusic

import com.example.playlistmaker.core.data.HISTORY
import com.example.playlistmaker.searchMusic.data.repository.MusicRepositoryImpl
import com.example.playlistmaker.searchMusic.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.MusicInteractorImpl
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractor
import com.example.playlistmaker.searchMusic.domain.interactor.SearchHistoryInteractorImpl
import com.example.playlistmaker.searchMusic.domain.repository.MusicRepository
import com.example.playlistmaker.searchMusic.domain.repository.SearchHistoryRepository
import com.example.playlistmaker.searchMusic.presentation.SearchMusicViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val searchModule = module {
    single<MusicRepository> { MusicRepositoryImpl(get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get(named(HISTORY))) }
    single<MusicInteractor> { MusicInteractorImpl(get()) }
    single<SearchHistoryInteractor> { SearchHistoryInteractorImpl(get()) }

    viewModel {
        SearchMusicViewModel(get(), get(), get())
    }
}


