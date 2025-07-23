package com.example.playlistmaker.searchMusic.presentation

import com.example.playlistmaker.searchMusic.domain.models.Track

sealed interface SearchMusicState {

    data object Loading : SearchMusicState

    data class ContentHistory(
        val tracks: List<Track>
    ) : SearchMusicState


    data class ContentSearchResult(
        val tracks: List<Track>
    ) : SearchMusicState

    data object Error : SearchMusicState

    data object Empty : SearchMusicState

}