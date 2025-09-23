package com.example.playlistmaker.core.data.db.data.converters

import com.example.playlistmaker.core.data.db.data.entity.MusicEntity
import com.example.playlistmaker.searchMusic.domain.models.Track

class MusicDbConvertor {
    fun map(music: Track): MusicEntity {
        return MusicEntity(
            music.id.toLong(),
            music.trackName,
            music.artistName,
            music.trackTimeMillis,
            music.artworkUrl100,
            music.collectionName,
            music.releaseDate,
            music.primaryGenreName,
            music.country,
            music.previewUrl
        )
    }

    fun map(music: MusicEntity): Track {
        return Track(
            music.id.toInt(),
            music.trackName,
            music.artistName,
            music.trackTimeMillis,
            music.artworkUrl100,
            music.collectionName,
            music.releaseDate,
            music.primaryGenreName,
            music.country,
            music.previewUrl,
            true
        )
    }
}