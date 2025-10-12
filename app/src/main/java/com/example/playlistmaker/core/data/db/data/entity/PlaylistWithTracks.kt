package com.example.playlistmaker.core.data.db.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithTracksEntity(
    @Embedded val playlist: PlayListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PlaylistTrackCrossRef::class,
            parentColumn = "playlistId",
            entityColumn = "trackId"
        )
    )
    val tracks: List<PlaylistTrackEntity>
){
    val tracksCount: Int get() = tracks.size
}

