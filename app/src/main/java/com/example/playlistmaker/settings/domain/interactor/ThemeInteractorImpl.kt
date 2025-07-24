package com.example.playlistmaker.settings.domain.interactor

import com.example.playlistmaker.settings.domain.repository.ThemeRepository

class ThemeInteractorImpl(private val repository: ThemeRepository) : ThemeInteractor {
    override fun initAppTheme() {
        setAppTheme(getAppTheme())
    }

    override fun getAppTheme(): Boolean {
        return repository.getAppTheme() ?: false
    }

    override fun setAppTheme(nightMode: Boolean) {
        repository.setAppTheme(nightMode)
    }


}
