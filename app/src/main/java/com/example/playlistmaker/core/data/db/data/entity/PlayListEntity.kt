package com.example.playlistmaker.core.data.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val url: String,
    val tracks: String,
    val tracksCount: Int,
)
