package com.example.playlistmaker.main

import android.app.Application
import com.example.playlistmaker.core.data.coreModule
import com.example.playlistmaker.core.data.db.dbModule
import com.example.playlistmaker.create_playlist.createPlayListModule
import com.example.playlistmaker.media.mediaModule
import com.example.playlistmaker.playList.playListModule
import com.example.playlistmaker.searchMusic.searchModule
import com.example.playlistmaker.settings.settingsModule
import com.example.playlistmaker.track.trackModule
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {

        PermissionRequester.initialize(applicationContext)
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                coreModule,
                dbModule,
                searchModule,
                settingsModule,
                mainModule,
                mediaModule,
                trackModule,
                createPlayListModule,
                playListModule
            )
        }
    }
}