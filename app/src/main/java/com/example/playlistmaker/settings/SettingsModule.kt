package com.example.playlistmaker.settings

import com.example.playlistmaker.core.data.APP_THEME
import com.example.playlistmaker.core.data.HISTORY
import com.example.playlistmaker.settings.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractorImpl
import com.example.playlistmaker.settings.domain.repository.ThemeRepository
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsModule = module {
    single<ThemeRepository> { ThemeRepositoryImpl(get(named(APP_THEME))) }
    single<ThemeInteractor> { ThemeInteractorImpl(get()) }

    viewModel {
        SettingsViewModel(get())
    }
}
