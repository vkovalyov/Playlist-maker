package com.example.playlistmaker.track

import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.track.bottomsheet.BottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trackModule = module {

    viewModel { (track: Track) ->
        TrackViewModel(track, get())
    }

    viewModel {
        BottomSheetViewModel(get())
    }

}


