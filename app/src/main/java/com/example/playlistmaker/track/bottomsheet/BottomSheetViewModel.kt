package com.example.playlistmaker.track.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val interactor: PlayListInteractor) :
    ViewModel() {
    private val stateLiveData = MutableLiveData<BottomSheetState>()
    fun observeState(): LiveData<BottomSheetState> = stateLiveData
    private val gson = Gson()

    fun getPlayList() {
        viewModelScope.launch {
          interactor.getAllPlaylistWithTracks().collect { plyList ->
                renderState(plyList)
            }
        }
    }


    fun addToPlayList(playListId: Long, track: Track) {
        viewModelScope.launch {
            interactor.insertToPlayList(track)
            interactor.addTrackToPlaylist(playListId, track.id.toLong())
            getPlayList()
        }

    }

    private fun listToJson(list: List<Long>): String {
        return gson.toJson(list)
    }

    private fun jsonToList(json: String): MutableList<Long> {
        if (json.isEmpty()) return mutableListOf()
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(json, listType)
    }

    private fun renderState(state: List<PlaylistWithTracks>) {
        stateLiveData.postValue(BottomSheetState.ContentPlayList(state))
    }


}