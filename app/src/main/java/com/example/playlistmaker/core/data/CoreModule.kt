package com.example.playlistmaker.core.data

import com.example.playlistmaker.core.data.cache.PrefsStorageClient
import com.example.playlistmaker.core.data.cache.StorageClient
import com.example.playlistmaker.core.data.network.NetworkClient
import com.example.playlistmaker.core.data.network.RetrofitNetworkClient
import com.example.playlistmaker.searchMusic.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

  const val APP_THEME = "app_theme"
  const val HISTORY = "history"

val coreModule = module {
    single { Gson() }

    single<NetworkClient> { RetrofitNetworkClient() }


    single<StorageClient<ArrayList<Track>>>(named(HISTORY)) {
        PrefsStorageClient(
            get(),
            this.androidContext(),
            HISTORY,
            object : TypeToken<ArrayList<Track>>() {}.type
        )
    }

    single<StorageClient<Boolean>>(named(APP_THEME)) {
        PrefsStorageClient(
            get(),
            this.androidContext(),
            APP_THEME,
            object : TypeToken<Boolean>() {}.type
        )
    }
}
