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
import com.example.playlistmaker.track.PlayerState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class CreatePlayListViewModel(
    private val interactor: PlayListInteractor,
    private val application: Context
) : ViewModel() {
    private val stateLiveData = MutableLiveData<CreatePlayListState>(CreatePlayListState.Content)
    fun observePlayerState(): LiveData<CreatePlayListState> = stateLiveData
    var name: String = ""
    var description: String = ""
    var path: Uri? = null

    fun canBack(): Boolean {
        return name.isNotEmpty() || description.isNotEmpty() || path != null
    }

    fun createPLayList() {
        viewModelScope.launch {
            val url = saveImageToPrivateStorage(path, name)
            interactor.insert(PlayList(0, name, description, url, "", 0))
                .collect {
                    stateLiveData.postValue(CreatePlayListState.Close(name))
                }
        }
    }

    private fun saveImageToPrivateStorage(uri: Uri?, name: String): String {
        if (uri == null) return ""
        val filePath =
            File(application.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file = File(filePath, "$name.jpg")
        val inputStream = application.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        return file.path
    }
}
