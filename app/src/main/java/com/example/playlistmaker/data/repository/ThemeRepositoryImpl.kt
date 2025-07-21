package com.example.playlistmaker.data.repository

import com.example.playlistmaker.cache.SharedPreferencesUtil
import com.example.playlistmaker.domain.repository.ThemeRepository

class ThemeRepositoryImpl() : ThemeRepository {
    override fun getAppTheme(): Boolean {
        return SharedPreferencesUtil.getAppTheme()
    }

    override fun setAppTheme(nightMode: Boolean) {
        SharedPreferencesUtil.setAppTheme(nightMode)
    }
}