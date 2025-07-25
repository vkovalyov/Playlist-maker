package com.example.playlistmaker.main

import android.app.Application
import com.example.playlistmaker.core.data.coreModule
import com.example.playlistmaker.media.mediaModule
import com.example.playlistmaker.searchMusic.searchModule
import com.example.playlistmaker.settings.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApplication)
            modules(coreModule, searchModule, settingsModule, mainModule, mediaModule)
        }
    }
}