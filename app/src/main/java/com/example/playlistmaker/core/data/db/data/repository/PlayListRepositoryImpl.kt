package com.example.playlistmaker.core.data.db.data.repository

import com.example.playlistmaker.core.data.db.data.converters.PlayListDbConvertor
import com.example.playlistmaker.core.data.db.data.dao.PlayListDao
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.repository.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class PlayListRepositoryImpl(
    private val playListDao: PlayListDao,
    private val playListDbConvertor: PlayListDbConvertor,
) : PlayListRepository {


    override fun insert(playList: PlayList): Flow<Unit> = flow {
        playListDao.insertPlayList(playListDbConvertor.map(playList))
        emit(Unit)
    }

    override fun getPlayList(): Flow<List<PlayList>> = flow {
        playListDao.getPlayList().collect {
            emit(convertFromPlayListEntity(it))
        }

    }

    override suspend fun getPlaylistById(id: Long): PlayList? {
        return convertFromPlayList(playListDao.getPlaylistById(id))
    }

    private fun convertFromPlayList(entity: PlayListEntity?): PlayList? {
        if (entity == null) return null
        return PlayList(
            entity.id,
            entity.name,
            entity.description,
            entity.url,
            entity.tracks,
            entity.tracksCount,
        )
    }

    private fun convertFromPlayListEntity(movies: List<PlayListEntity>): List<PlayList> {
        return movies.map { movie -> playListDbConvertor.map(movie) }
    }
}