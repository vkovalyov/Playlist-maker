package com.example.playlistmaker.track.bottomsheet

import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks


sealed interface BottomSheetState {

    data class ContentPlayList(val playList: List<PlaylistWithTracks>) : BottomSheetState

    data class SuccessAdd(val name: String) : BottomSheetState

    data class FailedAdd(val name: String) : BottomSheetState

}