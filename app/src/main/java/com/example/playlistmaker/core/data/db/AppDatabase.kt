package com.example.playlistmaker.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.core.data.db.data.dao.MusicDao
import com.example.playlistmaker.core.data.db.data.dao.PlayListDao
import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackCrossRef
import com.example.playlistmaker.core.data.db.data.entity.PlaylistTrackEntity

@Database(
    version = 2,
    exportSchema = false,
    entities = [
        MusicEntity::class, PlayListEntity::class, PlaylistTrackEntity::class,
        PlaylistTrackCrossRef::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
    abstract fun playListDao(): PlayListDao

}