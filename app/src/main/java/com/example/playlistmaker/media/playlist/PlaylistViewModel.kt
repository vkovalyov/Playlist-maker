package com.example.playlistmaker.media.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import kotlinx.coroutines.launch

class PlaylistViewModel(private val playListInteractor: PlayListInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<List<PlaylistWithTracks>>()
    fun observeState(): LiveData<List<PlaylistWithTracks>> = stateLiveData

    fun getPlayList() {
        viewModelScope.launch {
            playListInteractor.getAllPlaylistWithTracks().collect { playList ->
                stateLiveData.postValue(playList)

            }
        }
    }
}
