package com.example.playlistmaker.media.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import kotlinx.coroutines.launch

class PlaylistViewModel(private val playListInteractor: PlayListInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<List<PlayList>>()
    fun observeState(): LiveData<List<PlayList>> = stateLiveData

    fun getPlayList() {
        viewModelScope.launch {
            playListInteractor.getPlayList()
                .collect { playLists ->
                    renderState(playLists)
                }
        }
    }

    fun createList() {


    }

    private fun renderState(state: List<PlayList>) {
        stateLiveData.postValue(state)
    }
}
