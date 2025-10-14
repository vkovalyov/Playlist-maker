package com.example.playlistmaker.core.data.db.data.repository

import com.example.playlistmaker.core.data.db.data.converters.PlayListDbConvertor
import com.example.playlistmaker.core.data.db.data.dao.PlayListDao
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackCrossRef
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.core.data.db.domain.models.PlaylistTrack
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import com.example.playlistmaker.core.data.db.domain.repository.PlayListRepository
import com.example.playlistmaker.searchMusic.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class PlayListRepositoryImpl(
    private val playListDao: PlayListDao,
    private val playListDbConvertor: PlayListDbConvertor,
) : PlayListRepository {


    override suspend fun insert(playList: PlayList): Long  {
       return playListDao.insertPlayList(playListDbConvertor.map(playList))
    }

    override suspend fun getPlayList(): List<PlayList> {
        return convertFromPlayListEntity(playListDao.getPlayList())
    }

    override suspend fun getPlaylistById(id: Long): PlayList? {
        return convertFromPlayList(playListDao.getPlaylistById(id))
    }

    override suspend fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracks? {
        val entity = playListDao.getPlaylistWithTracks(playlistId) ?: return null
        val tracks = entity.tracks.map { convertFromPlayListTrack(it) }
        val playList = convertFromPlayList(entity.playlist)
        return PlaylistWithTracks(tracks = tracks, playlist = playList!!)
    }

    override fun getAllPlaylistWithTracks(): Flow<List<PlaylistWithTracks>> = flow {
        val entityList = playListDao.getAllPlaylistsWithTracks()
        entityList.collect {
            emit(it.map { entity ->
                PlaylistWithTracks(
                    playlist = convertFromPlayList(entity.playlist)!!,
                    tracks = entity.tracks.map { trackEntity -> convertFromPlayListTrack(trackEntity) }
                )
            })
        }
    }

    override suspend fun addTrackToPlaylist(playlistId: Long, trackId: Long) {
        playListDao.addTrackToPlaylist(
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = trackId
            )
        )
    }

    override suspend fun insertToPlayList(track: Track) {
        playListDao.insertTrack(convertFromPlayListTrackEntity(track))
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long) {
        playListDao.removeTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun deletePlaylistById(playlistId: Long) {
        playListDao.deletePlaylistById(playlistId)
    }

    override suspend fun updatePlaylist(playlist: PlayList) {
        playListDao.updatePlaylist(playListDbConvertor.map(playlist))
    }

    private fun convertFromPlayListTrackEntity(model: Track): PlaylistTrackEntity {
        return PlaylistTrackEntity(
            id = model.id.toLong(),
            trackName = model.trackName,
            artistName = model.artistName,
            trackTimeMillis = model.trackTimeMillis,
            artworkUrl100 = model.artworkUrl100,
            collectionName = model.collectionName,
            releaseDate = model.releaseDate,
            primaryGenreName = model.primaryGenreName,
            country = model.country,
            previewUrl = model.previewUrl,
        )
    }

    private fun convertFromPlayListTrack(entity: PlaylistTrackEntity): Track {
        return Track(
            id = entity.id.toInt(),
            trackName = entity.trackName,
            artistName = entity.artistName,
            trackTimeMillis = entity.trackTimeMillis,
            artworkUrl100 = entity.artworkUrl100,
            collectionName = entity.collectionName,
            releaseDate = entity.releaseDate,
            primaryGenreName = entity.primaryGenreName,
            country = entity.country,
            previewUrl = entity.previewUrl,
            isFavorite = false
        )
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