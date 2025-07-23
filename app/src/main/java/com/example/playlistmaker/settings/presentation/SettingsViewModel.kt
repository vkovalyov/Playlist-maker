package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.playlistmaker.settings.domain.interactor.ThemeInteractor


class SettingsViewModel(
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<Boolean>()
    fun observeState(): LiveData<Boolean> = stateLiveData

    fun setAppTheme(nightMode: Boolean){
        themeInteractor.setAppTheme(nightMode)
        getAppTheme()
    }

    fun getAppTheme(){
        stateLiveData.postValue(themeInteractor.getAppTheme())
    }

    companion object {

        fun getFactory(
            themeInteractor: ThemeInteractor
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    SettingsViewModel(themeInteractor)
                }
            }
    }
}