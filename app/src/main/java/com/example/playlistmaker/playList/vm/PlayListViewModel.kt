package com.example.playlistmaker.playList.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractor
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import kotlinx.coroutines.launch

class PlayListViewModel(
    private val id: Long,
    private val playListInteractor: PlayListInteractor,
    private val interactor: FavoriteMusicInteractor
) :
    ViewModel() {
    private val stateLiveData = MutableLiveData<PlayListState>()
    fun observeState(): LiveData<PlayListState> = stateLiveData

    fun deleteTrack(trackId: Long) {
        viewModelScope.launch {
            playListInteractor.removeTrackFromPlaylist(id, trackId)
        }
    }


    fun deletePlayListDialog() {
        stateLiveData.postValue(
            PlayListState.DeletePlayList(
                playlistWithTracks = stateLiveData.value!!.playlistWithTracks,
                minutes = stateLiveData.value!!.minutes
            )
        )
    }

    fun deletePlayList() {
        viewModelScope.launch {
            playListInteractor.deletePlaylistById(id)
        }
    }

    fun openMenu() {
        stateLiveData.postValue(
            PlayListState.OpenMenu(
                playlistWithTracks = stateLiveData.value!!.playlistWithTracks,
                minutes = stateLiveData.value!!.minutes
            )
        )
    }

    fun share() {
        stateLiveData.postValue(
            PlayListState.Share(
                playlistWithTracks = stateLiveData.value!!.playlistWithTracks,
                minutes = stateLiveData.value!!.minutes
            )
        )
    }

    fun editPlaylist(){
        stateLiveData.postValue(
            PlayListState.EditPlayList(
                playlistWithTracks = stateLiveData.value!!.playlistWithTracks,
                minutes = stateLiveData.value!!.minutes
            )
        )
    }

    fun getPlayList() {
        viewModelScope.launch {

            interactor.favoriteMusic()
                .collect { tracks ->
                    val favoritesId: List<Int> = tracks.map { it.id }

                    var timeMillis = 0
                    val playList = playListInteractor.getPlaylistWithTracks(id)

                    val withFavorite = playList?.tracks?.map { entity ->
                        timeMillis += entity.trackTimeMillis
                        if (favoritesId.isNotEmpty()) {
                            if (favoritesId.contains(entity.id)) {
                                entity.isFavorite = true
                            }
                        }
                        entity
                    } ?: emptyList()

                    stateLiveData.postValue(
                        PlayListState.Content(
                            playlistWithTracks = PlaylistWithTracks(
                                playlist = playList!!.playlist,
                                tracks = withFavorite
                            ),
                            minutes = timeMillis / 1000 / 60,
                        )
                    )
                }
        }
    }

}