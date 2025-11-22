package com.example.playlistmaker.playList

import com.example.playlistmaker.playList.vm.PlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playListModule = module {

    viewModel { (id: Long) ->
        PlayListViewModel(id, get(),get())
    }

}


