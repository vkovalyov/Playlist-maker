package com.example.playlistmaker.settings.domain.interactor


interface ThemeInteractor {
    fun initAppTheme()
    fun getAppTheme(): Boolean
    fun setAppTheme(nightMode: Boolean)
} 