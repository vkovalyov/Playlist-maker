package com.example.playlistmaker.core.data.db.domain.interactor

import com.example.playlistmaker.core.data.db.domain.repository.FavoriteMusicRepository
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteMusicInteractorImpl(
    private val favoriteMusicRepository: FavoriteMusicRepository
) : FavoriteMusicInteractor {

    override fun favoriteMusic(): Flow<List<Track>> {
        return favoriteMusicRepository.favoriteMusic()
    }

    override fun insert(track: Track): Flow<Unit> {
        return favoriteMusicRepository.insert(track)
    }

    override fun delete(id: Int): Flow<Unit> {
        return favoriteMusicRepository.delete(id)
    }

}