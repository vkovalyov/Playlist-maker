package com.example.playlistmaker.core.data.db.data.repository

import com.example.playlistmaker.core.data.db.AppDatabase
import com.example.playlistmaker.core.data.db.data.converters.MusicDbConvertor
import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import com.example.playlistmaker.core.data.db.domain.repository.FavoriteMusicRepository
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteMusicRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MusicDbConvertor,
) : FavoriteMusicRepository {

    override fun favoriteMusic(): Flow<List<Track>> = flow {
         appDatabase.musicDao().getMusics().collect{
            emit(convertFromMovieEntity(it))
        }

    }

    override fun insert(track: Track): Flow<Unit> = flow {
        appDatabase.musicDao().insertMusic(movieDbConvertor.map(track))
        emit(Unit)
    }

    override fun delete(id: Int): Flow<Unit> = flow {
        appDatabase.musicDao().deleteMusicById(id.toLong())
        emit(Unit)
    }

    private fun convertFromMovieEntity(movies: List<MusicEntity>): List<Track> {
        return movies.map { movie -> movieDbConvertor.map(movie) }
    }
}