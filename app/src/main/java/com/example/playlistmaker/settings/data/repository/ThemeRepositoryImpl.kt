package com.example.playlistmaker.settings.data.repository

import com.example.playlistmaker.core.cache.SharedPreferencesUtil
import com.example.playlistmaker.settings.domain.repository.ThemeRepository

class ThemeRepositoryImpl() : ThemeRepository {
    override fun getAppTheme(): Boolean {
        return SharedPreferencesUtil.getAppTheme()
    }

    override fun setAppTheme(nightMode: Boolean) {
        SharedPreferencesUtil.setAppTheme(nightMode)
    }
}