package com.example.playlistmaker.core.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackCrossRef
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackEntity
import com.example.playlistmaker.core.data.db.data.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayList(track: PlayListEntity)

    @Query("SELECT * FROM playlist_table")
    suspend fun getPlayList(): List<PlayListEntity>

    @Query("SELECT * FROM playlist_table WHERE id = :playlistId")
    suspend fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracksEntity?

    @Query("SELECT * FROM playlist_table WHERE id = :id")
    suspend fun getPlaylistById(id: Long): PlayListEntity?

    @Transaction
    @Query("SELECT * FROM playlist_table")
    suspend fun getAllPlaylistsWithTracks(): List<PlaylistWithTracksEntity>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(track: PlaylistTrackEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrackToPlaylist(crossRef: PlaylistTrackCrossRef)

}
