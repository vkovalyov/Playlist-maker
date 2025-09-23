package com.example.playlistmaker.media.vm.favorite

import com.example.playlistmaker.searchMusic.domain.models.Track

sealed interface FavoriteState {

    data object Loading : FavoriteState

    data class ContentFavorite(
        val tracks: List<Track>
    ) : FavoriteState



}