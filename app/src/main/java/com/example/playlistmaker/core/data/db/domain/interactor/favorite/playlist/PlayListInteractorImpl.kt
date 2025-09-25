package com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist

import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.repository.FavoriteMusicRepository
import com.example.playlistmaker.core.data.db.domain.repository.PlayListRepository
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(
    private val repository: PlayListRepository
) : PlayListInteractor {

    override fun getPlayList(): Flow<List<PlayList>> {
        return repository.getPlayList()
    }

    override fun insert(playList: PlayList): Flow<Unit> {
        return repository.insert(playList)
    }

    override suspend fun getPlaylistById(id: Long): PlayList? {
        return repository.getPlaylistById(id)
    }
}