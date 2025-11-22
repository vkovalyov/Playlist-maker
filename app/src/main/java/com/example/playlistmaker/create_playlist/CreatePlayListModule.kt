package com.example.playlistmaker.create_playlist

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createPlayListModule = module {

    viewModel { (playList: PlayList) ->
        CreatePlayListViewModel(playList,get(),androidContext())
    }
}