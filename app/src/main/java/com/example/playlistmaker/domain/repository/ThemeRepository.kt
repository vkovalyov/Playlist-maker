package com.example.playlistmaker.domain.repository

interface ThemeRepository {
    fun getAppTheme() : Boolean
    fun setAppTheme(nightMode: Boolean)
}