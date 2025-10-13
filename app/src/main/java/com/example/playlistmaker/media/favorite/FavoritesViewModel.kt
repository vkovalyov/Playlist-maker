package com.example.playlistmaker.media.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractor
import kotlinx.coroutines.launch


class FavoritesViewModel(private val favorite: FavoriteMusicInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<FavoriteState>()
    fun observeState(): LiveData<FavoriteState> = stateLiveData


    fun getFavorites() {
        viewModelScope.launch {
            favorite.favoriteMusic()
//                .stateIn(
//                    scope = viewModelScope,
//                    started = SharingStarted.WhileSubscribed(5000),
//                    initialValue = emptyList()
//                )
                .collect { favorite ->
                    renderState(FavoriteState.ContentFavorite(favorite))
                }

        }
    }

    private fun renderState(state: FavoriteState) {
        stateLiveData.postValue(state)
    }

}