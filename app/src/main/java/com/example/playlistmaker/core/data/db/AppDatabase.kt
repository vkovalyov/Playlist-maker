package com.example.playlistmaker.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.core.data.db.data.dao.MusicDao
import com.example.playlistmaker.core.data.db.data.dao.PlayListDao
import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity

@Database(version = 1, entities = [MusicEntity::class,PlayListEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
    abstract fun playListDao(): PlayListDao

}