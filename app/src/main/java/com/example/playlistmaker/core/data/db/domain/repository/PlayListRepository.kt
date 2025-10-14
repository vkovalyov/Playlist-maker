package com.example.playlistmaker.core.data.db.domain.repository

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    fun insert(playList: PlayList): Flow<Unit>
    suspend fun getPlayList(): List<PlayList>
    suspend fun getPlaylistById(id: Long): PlayList?
    suspend fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracks?
    fun getAllPlaylistWithTracks(): Flow<List<PlaylistWithTracks>>
    suspend fun addTrackToPlaylist(
        playlistId: Long,
        trackId: Long
    )

    suspend fun insertToPlayList(track: Track)

    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long)
    suspend fun deletePlaylistById(playlistId: Long)

    suspend fun updatePlaylist(playlist: PlayList)
}