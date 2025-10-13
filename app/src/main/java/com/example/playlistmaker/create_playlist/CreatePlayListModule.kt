package com.example.playlistmaker.create_playlist

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createPlayListModule = module {
    viewModel {
        CreatePlayListViewModel(get(),androidContext())
    }
}