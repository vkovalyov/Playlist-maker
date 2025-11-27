package com.example.playlistmaker.core.service

import com.example.playlistmaker.track.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface AudioPlayerControl {
    fun getPlayerState(): StateFlow<PlayerState>
    fun startPlayer()
    fun pausePlayer()

    fun showNotification(title: String, description: String)
    fun hiddenNotification()
}