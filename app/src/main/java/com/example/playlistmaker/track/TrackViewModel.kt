package com.example.playlistmaker.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractor
import com.example.playlistmaker.core.service.AudioPlayerControl
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.launch


val PUSH_TITLE = "Playlist Maker"

class TrackViewModel(private val track: Track, private val interactor: FavoriteMusicInteractor) :
    ViewModel() {
    private val playerStateLiveData = MutableLiveData<PlayerState>(PlayerState.Default())
    private val favoriteLiveData = MutableLiveData<Boolean>(track.isFavorite)

    fun observePlayerState(): LiveData<PlayerState> = playerStateLiveData
    fun observeFavoriteState(): LiveData<Boolean> = favoriteLiveData

    private var audioPlayerControl: AudioPlayerControl? = null
    private var isFavorite: Boolean = track.isFavorite


    fun setAudioPlayerControl(audioPlayerControl: AudioPlayerControl) {
        this.audioPlayerControl = audioPlayerControl

        viewModelScope.launch {
            audioPlayerControl.getPlayerState().collect {
                if(!it.isPlayButtonEnabled){
                    hiddenNotification()
                }
                playerStateLiveData.postValue(it)
            }
        }
    }

    fun showNotification() {
        this.audioPlayerControl?.showNotification(
            PUSH_TITLE,
            "${track.artistName} - ${track.trackName}"
        )
    }

    fun hiddenNotification() {
        this.audioPlayerControl?.hiddenNotification()

    }

    fun onPlayerButtonClicked() {
        if (playerStateLiveData.value is PlayerState.Playing) {
            audioPlayerControl?.pausePlayer()
        } else {
            audioPlayerControl?.startPlayer()
        }
    }

    fun removeAudioPlayerControl() {
        audioPlayerControl = null
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayerControl = null
    }

    fun onFavoriteClicked(track: Track) {
        isFavorite = !isFavorite
        viewModelScope.launch {

            if (!isFavorite) {
                interactor.delete(track.id).collect {
                    favoriteLiveData.postValue(isFavorite)
                }
            } else {
                interactor.insert(track).collect {
                    favoriteLiveData.postValue(isFavorite)
                }
            }
            interactor.favoriteMusic()

        }
    }


}