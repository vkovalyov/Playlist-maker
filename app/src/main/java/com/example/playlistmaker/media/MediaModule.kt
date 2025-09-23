package com.example.playlistmaker.media

import com.example.playlistmaker.media.vm.favorite.FavoritesViewModel
import com.example.playlistmaker.media.vm.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {
    viewModel {
        PlaylistViewModel()
        FavoritesViewModel(get())
    }
}