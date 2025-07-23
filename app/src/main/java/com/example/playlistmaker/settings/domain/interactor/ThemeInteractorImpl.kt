package com.example.playlistmaker.settings.domain.interactor

import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.settings.domain.repository.ThemeRepository

class ThemeInteractorImpl(private val repository: ThemeRepository) : ThemeInteractor {
    override fun initAppTheme() {
        setAppTheme(getAppTheme())
    }

    override fun getAppTheme(): Boolean {
        return repository.getAppTheme()
    }

    override fun setAppTheme(nightMode: Boolean) {
        repository.setAppTheme(nightMode)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}
