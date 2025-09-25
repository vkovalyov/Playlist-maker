package com.example.playlistmaker.core.data.db.data.converters

import com.example.playlistmaker.core.data.db.data.entity.PlayListEntity
import com.example.playlistmaker.core.data.db.domain.models.PlayList

class PlayListDbConvertor {
    fun map(playList: PlayList): PlayListEntity {
        return PlayListEntity(
            playList.id,
            playList.name,
            playList.description,
            playList.url,
            playList.tracks,
            playList.tracksCount,
        )
    }

    fun map(playList: PlayListEntity): PlayList {
        return PlayList(
            playList.id,
            playList.name,
            playList.description,
            playList.url,
            playList.tracks,
            playList.tracksCount,
        )
    }
}