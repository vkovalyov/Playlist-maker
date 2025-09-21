package com.example.playlistmaker.settings.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor

class SettingsViewModel(
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<Boolean>()
    fun observeState(): LiveData<Boolean> = stateLiveData

    fun setAppTheme(nightMode: Boolean) {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        themeInteractor.setAppTheme(nightMode)
        getAppTheme()
    }

    fun getAppTheme() {
        stateLiveData.postValue(themeInteractor.getAppTheme())
    }
}