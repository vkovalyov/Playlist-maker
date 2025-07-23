package com.example.playlistmaker.settings.domain.repository

interface ThemeRepository {
    fun getAppTheme() : Boolean
    fun setAppTheme(nightMode: Boolean)
}