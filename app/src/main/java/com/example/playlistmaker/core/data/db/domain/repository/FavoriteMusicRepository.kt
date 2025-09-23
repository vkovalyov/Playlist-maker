package com.example.playlistmaker.core.data.db.domain.repository


import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteMusicRepository {
    fun insert(track: Track): Flow<Unit>
    fun delete(id: Int): Flow<Unit>
    fun favoriteMusic(): Flow<List<Track>>
}