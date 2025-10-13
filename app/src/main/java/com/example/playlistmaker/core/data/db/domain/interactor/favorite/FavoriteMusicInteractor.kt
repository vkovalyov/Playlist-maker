package com.example.playlistmaker.core.data.db.domain.interactor.favorite

 import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface FavoriteMusicInteractor {

    fun favoriteMusic(): Flow<List<Track>>
    fun insert(track: Track): Flow<Unit>
    fun delete(id: Int): Flow<Unit>
}