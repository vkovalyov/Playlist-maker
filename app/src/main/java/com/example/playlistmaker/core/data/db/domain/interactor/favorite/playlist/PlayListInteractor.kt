package com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    fun getPlayList(): Flow<List<PlayList>>
    fun insert(playList: PlayList): Flow<Unit>
    suspend fun getPlaylistById(id:Long):PlayList?
}