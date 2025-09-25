package com.example.playlistmaker.core.data.db.domain.repository


import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    fun insert(playList: PlayList): Flow<Unit>
    fun getPlayList(): Flow<List<PlayList>>
    suspend fun getPlaylistById(id:Long):PlayList?
}