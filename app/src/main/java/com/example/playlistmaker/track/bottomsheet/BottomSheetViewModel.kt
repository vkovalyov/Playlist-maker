package com.example.playlistmaker.track.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val interactor: PlayListInteractor) :
    ViewModel() {
    private val stateLiveData = MutableLiveData<BottomSheetState>()
    fun observeState(): LiveData<BottomSheetState> = stateLiveData
    private var plaLists: List<PlaylistWithTracks> = emptyList()

    fun getPlayList() {
        viewModelScope.launch {
            interactor.getAllPlaylistWithTracks().collect { plyList ->
                renderState(plyList)
            }
        }
    }

    fun addToPlayList(playListId: Long, track: Track) {
        viewModelScope.launch {
            val playlist = plaLists.find { it.playlist.id == playListId }
            val findTrack = playlist?.tracks?.find { it.id == track.id }
            if (findTrack == null) {
                interactor.insertToPlayList(track)
                interactor.addTrackToPlaylist(playListId, track.id.toLong())
                getPlayList()
                stateLiveData.postValue(BottomSheetState.SuccessAdd(playlist?.playlist?.name ?: ""))
            } else {
                stateLiveData.postValue(BottomSheetState.FailedAdd(playlist.playlist?.name ?: ""))
            }
        }

    }

    private fun renderState(state: List<PlaylistWithTracks>) {
        plaLists = state
        stateLiveData.postValue(BottomSheetState.ContentPlayList(state))
    }


}