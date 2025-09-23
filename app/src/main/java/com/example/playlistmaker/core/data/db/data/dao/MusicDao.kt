package com.example.playlistmaker.core.data.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(track: MusicEntity)

    @Query("DELETE FROM music_table WHERE id = :trackId")
    suspend fun deleteMusicById(trackId: Long)

    @Query("SELECT * FROM music_table ORDER BY createdAt DESC")
    fun getMusics(): Flow<List<MusicEntity>>
}
