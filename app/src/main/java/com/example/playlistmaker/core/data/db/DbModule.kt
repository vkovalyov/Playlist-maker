package com.example.playlistmaker.core.data.db

import androidx.room.Room
import com.example.playlistmaker.core.data.db.data.converters.MusicDbConvertor
import com.example.playlistmaker.core.data.db.data.converters.PlayListDbConvertor
import com.example.playlistmaker.core.data.db.data.repository.FavoriteMusicRepositoryImpl
import com.example.playlistmaker.core.data.db.data.repository.PlayListRepositoryImpl
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractor
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.FavoriteMusicInteractorImpl
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractor
import com.example.playlistmaker.core.data.db.domain.interactor.favorite.playlist.PlayListInteractorImpl
import com.example.playlistmaker.core.data.db.domain.repository.FavoriteMusicRepository
import com.example.playlistmaker.core.data.db.domain.repository.PlayListRepository
import org.koin.android.ext.koin.androidContext


import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database.db"
        ).build()
    }
    single { get<AppDatabase>().musicDao() }
    single { get<AppDatabase>().playListDao() }
    single<FavoriteMusicRepository> { FavoriteMusicRepositoryImpl(get(), get()) }
    single<PlayListRepository> { PlayListRepositoryImpl(get(), get()) }

    factory { MusicDbConvertor() }
    factory { PlayListDbConvertor() }

    single<FavoriteMusicInteractor> { FavoriteMusicInteractorImpl(get()) }
    single<PlayListInteractor> { PlayListInteractorImpl(get()) }
}


