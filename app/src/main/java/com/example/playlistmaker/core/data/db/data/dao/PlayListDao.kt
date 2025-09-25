package com.example.playlistmaker.core.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayList(track: PlayListEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlayList(): Flow<List<PlayListEntity>>

    @Query("SELECT * FROM playlist_table WHERE id = :id")
    suspend fun getPlaylistById(id: Long): PlayListEntity?
}
