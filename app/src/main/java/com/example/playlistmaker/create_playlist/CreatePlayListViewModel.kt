package com.example.playlistmaker.create_playlist


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.models.PlayList
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.example.playlistmaker.track.PlayerState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CreatePlayListViewModel(
    private val playList: PlayList?,
    private val interactor: PlayListInteractor,
    private val application: Context
) : ViewModel() {
    var name: String = ""
    var description: String = ""
    var path: Uri? = null
    private val stateLiveData = MutableLiveData<CreatePlayListState>(
        CreatePlayListState.Content
    )

    fun observePlayerState(): LiveData<CreatePlayListState> = stateLiveData

    init {
        if (playList != null) {
            name = playList.name
            description = playList.description
            if (playList.url.isNotEmpty()) {
                path = Uri.parse(playList.url)
            }
            stateLiveData.postValue(
                CreatePlayListState.EditContent(
                    name = name,
                    description = description,
                    uri = path
                )
            )
        }
    }

    fun canBack(): Boolean {
        return name.isNotEmpty() || description.isNotEmpty() || path != null
    }

    fun createPLayList() {
        viewModelScope.launch {
            if (playList == null) {
                val newPlayListId = interactor.insert(PlayList(0, name, description, "", "", 0))

                val playList = interactor.getPlaylistById(newPlayListId)
                if (playList != null) {
                    val url = saveImageToPrivateStorage(path, playList.id)
                    interactor.updatePlaylist(PlayList(playList.id, name, description, url, "", 0))
                    stateLiveData.postValue(CreatePlayListState.Close(name))
                }
            } else {
                var newUrl: String = ""
                newUrl = if (path?.path != playList.url) {
                    saveImageToPrivateStorage(path, playList.id)
                } else {
                    playList.url
                }
                interactor.updatePlaylist(PlayList(playList.id, name, description, newUrl, "", 0))
                stateLiveData.postValue(CreatePlayListState.Close(name))

            }
        }
    }

    private fun saveImageToPrivateStorage(uri: Uri?, playlistId: Long): String {
        if (uri == null) return ""
        val fileName = "$playlistId.jpg"

        return try {
            val inputStream = application.contentResolver.openInputStream(uri)
                ?: throw IOException("Failed to open input stream")

            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val filesDir = application.filesDir
            val file = File(filesDir, fileName)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }

            file.absolutePath

        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
