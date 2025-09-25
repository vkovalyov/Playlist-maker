package com.example.playlistmaker.track.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val interactor: PlayListInteractor) :
    ViewModel() {
    private val stateLiveData = MutableLiveData<BottomSheetState>()
    fun observeState(): LiveData<BottomSheetState> = stateLiveData
    private val gson = Gson()

    fun getPlayList() {
        viewModelScope.launch {
            interactor.getPlayList()
                .collect { playLists ->
                    renderState(playLists)
                }
        }
    }


    fun addToPlayList(playListId: Long, trackId: Long) {
        viewModelScope.launch {
            val playList = interactor.getPlaylistById(playListId)
            if (playList != null) {
                var tracks = jsonToList(playList.tracks)
                if (tracks.isEmpty() || !tracks.contains(trackId)) {
                    tracks.add(trackId)
                    interactor.insert(
                        PlayList(
                            playList.id,
                            playList.name,
                            playList.description,
                            playList.url,
                            listToJson(tracks),
                            tracks.size,
                        )
                    ).collect {
                        stateLiveData.postValue(BottomSheetState.SuccessAdd(playList.name))
                    }
                } else {
                    stateLiveData.postValue(BottomSheetState.FailedAdd(playList.name))
                }
            }
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

    private fun renderState(state: List<PlayList>) {
        stateLiveData.postValue(BottomSheetState.ContentPlayList(state))
    }


}