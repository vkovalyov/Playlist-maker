package com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.core.data.db.domain.repository.PlayListRepository
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(
    private val repository: PlayListRepository
) : PlayListInteractor {

    override suspend fun getPlayList(): List<PlayList> {
        return repository.getPlayList()
    }

    override suspend fun insert(playList: PlayList): Long {
        return repository.insert(playList)
    }

    override suspend fun getPlaylistById(id: Long): PlayList? {
        return repository.getPlaylistById(id)
    }

    override suspend fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracks? {
        return repository.getPlaylistWithTracks(playlistId)
    }

    override fun getAllPlaylistWithTracks(): Flow<List<PlaylistWithTracks>> {
      return  repository.getAllPlaylistWithTracks()
    }

    override suspend fun addTrackToPlaylist(playlistId: Long, trackId: Long) {
        return repository.addTrackToPlaylist(playlistId = playlistId, trackId = trackId)
    }

    override suspend fun insertToPlayList(track: Track) {
        return repository.insertToPlayList(track)
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long) {
        return repository.removeTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun deletePlaylistById(playlistId: Long) {
        return repository.deletePlaylistById(playlistId)
    }

    override suspend fun updatePlaylist(playlist: PlayList) {
        return repository.updatePlaylist(playlist)
    }
}