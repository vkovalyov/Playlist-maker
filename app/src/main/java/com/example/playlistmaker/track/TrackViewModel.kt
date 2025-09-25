package com.example.playlistmaker.track

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractor
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

private const val DELAY = 300L;


class TrackViewModel(private val track: Track, private val interactor: FavoriteMusicInteractor) :
    ViewModel() {
    private val playerStateLiveData = MutableLiveData<PlayerState>(PlayerState.Default())
    private val favoriteLiveData = MutableLiveData<Boolean>(track.isFavorite)
    private var timerJob: Job? = null

    fun observePlayerState(): LiveData<PlayerState> = playerStateLiveData
    fun observeFavoriteState(): LiveData<Boolean> = favoriteLiveData

    private var isFavorite: Boolean = track.isFavorite

    private val mediaPlayer = MediaPlayer()


    init {
        preparePlayer()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        mediaPlayer.release()
        playerStateLiveData.value = PlayerState.Default()
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

    fun onPlayButtonClicked() {
        when (playerStateLiveData.value) {
            is PlayerState.Playing -> {
                pausePlayer()
            }

            is PlayerState.Prepared, is PlayerState.Paused -> {
                startPlayer()
            }

            else -> {}
        }
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            playerStateLiveData.postValue(PlayerState.Prepared())
        }
        mediaPlayer.setOnCompletionListener {
            timerJob?.cancel()
            playerStateLiveData.postValue(PlayerState.Prepared())
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerStateLiveData.postValue(PlayerState.Playing(getCurrentPlayerPosition()))
        startTimerUpdate()
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        timerJob?.cancel()
        playerStateLiveData.postValue(PlayerState.Paused(getCurrentPlayerPosition()))
    }

    private fun startTimerUpdate() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (mediaPlayer.isPlaying) {
                delay(DELAY)
                playerStateLiveData.postValue(
                    PlayerState.Playing(getCurrentPlayerPosition())
                )
            }
        }
    }

    private fun getCurrentPlayerPosition(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
            ?: "00:00"
    }
}