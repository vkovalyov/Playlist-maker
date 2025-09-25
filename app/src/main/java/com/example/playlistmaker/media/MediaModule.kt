package com.example.playlistmaker.media

import com.example.playlistmaker.media.favorite.FavoritesViewModel
import com.example.playlistmaker.media.playlist.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    viewModel {
        FavoritesViewModel(get())
    }

    viewModel{
        PlaylistViewModel(get())
    }
}