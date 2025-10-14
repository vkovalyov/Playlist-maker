package com.example.playlistmaker.create_playlist

import android.net.Uri

sealed class CreatePlayListState() {
    data object Content : CreatePlayListState()
    class CreateContent(val name: String, val description: String, val uri: Uri?) : CreatePlayListState()
    class EditContent(val name: String, val description: String, val uri: Uri?) : CreatePlayListState()
    class Close(val name: String) : CreatePlayListState()

}