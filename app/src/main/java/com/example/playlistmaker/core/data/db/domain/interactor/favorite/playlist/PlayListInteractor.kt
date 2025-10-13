package com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistTrack
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun getPlayList(): List<PlayList>
    fun insert(playList: PlayList): Flow<Unit>
    suspend fun getPlaylistById(id:Long):PlayList?
    suspend fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracks?
    suspend fun getAllPlaylistWithTracks(): List<PlaylistWithTracks>?
    suspend fun addTrackToPlaylist(
        playlistId: Long,
        trackId: Long
    )
    suspend fun insertToPlayList(track: Track)
}