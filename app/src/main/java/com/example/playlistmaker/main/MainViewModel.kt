package com.example.playlistmaker.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor

class MainViewModel(
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    fun initTheme() {
        setAppTheme(getAppTheme())
    }

    private fun setAppTheme(nightMode: Boolean) {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getAppTheme(): Boolean {
        return themeInteractor.getAppTheme()
    }
}