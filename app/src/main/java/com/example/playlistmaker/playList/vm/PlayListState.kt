package com.example.playlistmaker.playList.vm

import com.example.playlistmaker.core.data.db.domain.models.PlaylistWithTracks


sealed class PlayListState(
    val playlistWithTracks: PlaylistWithTracks,
    val minutes: Int
) {

    class Content(playlistWithTracks: PlaylistWithTracks, minutes: Int) :
        PlayListState(playlistWithTracks, minutes)

    class Share(playlistWithTracks: PlaylistWithTracks, minutes: Int) :
        PlayListState(playlistWithTracks, minutes)

    class OpenMenu(playlistWithTracks: PlaylistWithTracks, minutes: Int) :
        PlayListState(playlistWithTracks, minutes)

    class EditPlayList(playlistWithTracks: PlaylistWithTracks, minutes: Int) :
        PlayListState(playlistWithTracks, minutes)

    class DeletePlayList(playlistWithTracks: PlaylistWithTracks, minutes: Int) :
        PlayListState(playlistWithTracks, minutes)
}