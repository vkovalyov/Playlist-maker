package com.example.playlistmaker.main


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    viewModel {
        MainViewModel(get())
    }
}
