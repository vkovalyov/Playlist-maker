package com.example.playlistmaker.create_playlist

sealed class CreatePlayListState() {

    data object Content : CreatePlayListState()
      class Close(val name: String) : CreatePlayListState()

}