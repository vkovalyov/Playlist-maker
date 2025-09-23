package com.example.playlistmaker.core.data.db

import androidx.room.Room
import com.example.playlistmaker.core.data.db.data.converters.MusicDbConvertor
import com.example.playlistmaker.core.data.db.data.dao.MusicDao
import com.example.playlistmaker.core.data.db.data.repository.FavoriteMusicRepositoryImpl
import com.example.playlistmaker.core.data.db.domain.interactor.FavoriteMusicInteractor
import com.example.playlistmaker.core.data.db.domain.interactor.FavoriteMusicInteractorImpl
import com.example.playlistmaker.core.data.db.domain.repository.FavoriteMusicRepository
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
    single<FavoriteMusicRepository> { FavoriteMusicRepositoryImpl(get(), get()) }

    factory { MusicDbConvertor() }

    single<FavoriteMusicInteractor> { FavoriteMusicInteractorImpl(get()) }
}


