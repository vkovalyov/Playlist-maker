package com.example.playlistmaker.settings.data.repository

import com.example.playlistmaker.core.data.cache.StorageClient
import com.example.playlistmaker.settings.domain.repository.ThemeRepository

class ThemeRepositoryImpl(private val storage: StorageClient<Boolean>) : ThemeRepository {
    override fun getAppTheme(): Boolean {
        return storage.getData() ?: false
    }

    override fun setAppTheme(nightMode: Boolean) {
        storage.storeData(nightMode)
    }
}