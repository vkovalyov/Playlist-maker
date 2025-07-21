package com.example.playlistmaker.domain.interactor


interface ThemeInteractor {
    fun initAppTheme()
    fun getAppTheme(): Boolean
    fun setAppTheme(nightMode: Boolean)
} 